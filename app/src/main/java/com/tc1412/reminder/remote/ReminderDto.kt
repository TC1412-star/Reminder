package com.tc1412.reminder.remote

import com.google.gson.annotations.SerializedName
import com.tc1412.reminder.Category
import com.tc1412.reminder.Priority
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.database.ReminderEntity
import java.time.LocalDateTime

data class ReminderDto(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val category: String = "PERSONAL",
    val priority: String = "LOW",
    val dateTime: String = ""
)

fun ReminderEntity.toDto(): ReminderDto = ReminderDto(
    id = remoteId, title = name, description = description,
    category = when (category) { Category.Work -> "WORK"; Category.Social -> "SOCIAL"; else -> "PERSONAL" },
    priority = when (priority) { Priority.High -> "HIGH"; Priority.Medium -> "MEDIUM"; else -> "LOW" },
    dateTime = dateTime.toString()
)

fun ReminderDto.toEntity(localId: Long = 0): ReminderEntity = ReminderEntity(
    id = localId, remoteId = id, name = title, description = description,
    category = when (category.uppercase()) { "WORK" -> Category.Work; "SOCIAL" -> Category.Social; else -> Category.Personal },
    priority = when (priority.uppercase()) { "HIGH" -> Priority.High; "MEDIUM" -> Priority.Medium; else -> Priority.Low },
    dateTime = runCatching { LocalDateTime.parse(dateTime) }.getOrDefault(LocalDateTime.now()),
    syncStatus = "SYNCED"
)

fun ReminderDto.toDomain(localId: Long): Reminder = toEntity(localId).let(Reminder::fromEntity)
