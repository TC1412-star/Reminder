package com.tc1412.reminder.repositories

import com.tc1412.reminder.Reminder
import com.tc1412.reminder.database.ReminderDao
import com.tc1412.reminder.database.ReminderEntity
import com.tc1412.reminder.remote.ReminderService
import com.tc1412.reminder.remote.toDto
import com.tc1412.reminder.remote.toEntity
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.time.ZoneOffset

class DatabaseException(val localReminders: List<Reminder>) : Exception()
class DatabaseRepository(
    private val dao: ReminderDao,
    private val remote: ReminderService
) : ReminderRepository {
    override suspend fun getReminders(): List<Reminder> {
        var list: List<ReminderEntity>
        try {
            syncPending()
            list = remote.getReminders().map { it.toEntity() }
            dao.storeAll(list)
        } catch (e: Exception) {
            throw DatabaseException(dao.getAll().first().map(Reminder::fromEntity))
        }
        return list.map(Reminder::fromEntity)
    }

    override suspend fun storeReminder(rem: Reminder) {
        val previous = dao.getById(rem.id)
        dao.store(rem.toEntity().copy(remoteId = previous?.remoteId, syncStatus = "PENDING"))
        runCatching { syncPending() }
    }

    override suspend fun removeReminder(id: Long) {
        val entity = dao.getById(id) ?: return
        if (entity.remoteId == null) dao.removePermanently(id)
        else dao.markDeleted(id)
        runCatching { syncPending() }
    }

    override suspend fun newID(): Long = dao.getAll().first().maxOfOrNull { it.id }?.plus(1) ?: 0

    suspend fun getPendingReminders(): List<Reminder> {
        val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        return dao.getPending(now).map(Reminder::fromEntity)
    }

    suspend fun syncPending() {
        val service = remote
        dao.getPendingSync().forEach { item ->
            try {
                if (item.isDeleted) {
                    val remoteId = item.remoteId
                    if (remoteId != null) service.deleteReminder(remoteId)
                    dao.removePermanently(item.id)
                } else if (item.remoteId == null) {
                    val response = service.createReminder(item.toDto())
                    dao.updateSyncState(item.id, "SYNCED", response.id?.toString())
                } else {
                    service.updateReminder(item.remoteId, item.toDto())
                    dao.updateSyncState(item.id, "SYNCED")
                }
            } catch (e: Exception) {
                dao.updateSyncState(item.id, "PENDING")
            }
        }
    }
}
