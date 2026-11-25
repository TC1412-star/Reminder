package com.tc1412.reminder.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE is_deleted = 0 ORDER BY date_time ASC, id ASC")
    fun getAll(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE is_deleted = 0 AND date_time >= :now ORDER BY date_time ASC")
    suspend fun getPending(now: Long): List<ReminderEntity>

    @Query("SELECT * FROM reminders WHERE id = :id AND is_deleted = 0 LIMIT 1")
    suspend fun getById(id: Long): ReminderEntity?

    @Upsert
    suspend fun store(reminder: ReminderEntity)

    @Upsert
    suspend fun storeAll(reminders: List<ReminderEntity>)

    @Query("UPDATE reminders SET is_deleted = 1, sync_status = 'PENDING' WHERE id = :id")
    suspend fun markDeleted(id: Long): Int

    @Query("SELECT * FROM reminders WHERE sync_status = 'PENDING' ORDER BY id ASC")
    suspend fun getPendingSync(): List<ReminderEntity>

    @Query("UPDATE reminders SET sync_status = :status, remote_id = COALESCE(:remoteId, remote_id) WHERE id = :id")
    suspend fun updateSyncState(id: Long, status: String, remoteId: String? = null)

    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun removePermanently(id: Long): Int
}
