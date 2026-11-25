package com.tc1412.reminder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tc1412.reminder.Category
import com.tc1412.reminder.Priority
import java.time.LocalDateTime

@Entity(tableName = "reminders", indices = [Index(value = ["remote_id"], unique = true)])
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "remote_id") val remoteId: Int? = null,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "category") val category: Category = Category.Personal,
    @ColumnInfo(name = "priority") val priority: Priority = Priority.Low,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "sync_status", defaultValue = "'PENDING'") val syncStatus: String = "PENDING",
    @ColumnInfo(name = "is_deleted", defaultValue = "0") val isDeleted: Boolean = false
)
