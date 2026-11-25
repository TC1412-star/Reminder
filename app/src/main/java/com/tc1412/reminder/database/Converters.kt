package com.tc1412.reminder.database

import androidx.room.TypeConverter
import com.tc1412.reminder.Category
import com.tc1412.reminder.Priority
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun dateTimeToLong(date: LocalDateTime): Long = date.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun longToDateTime(value: Long): LocalDateTime =
        LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)

    @TypeConverter
    fun categoryToString(category: Category): String = when (category) {
        Category.Work -> "WORK"
        Category.Social -> "SOCIAL"
        Category.Personal -> "PERSONAL"
    }

    @TypeConverter
    fun stringToCategory(value: String): Category = when (value) {
        "WORK" -> Category.Work
        "SOCIAL" -> Category.Social
        "PERSONAL" -> Category.Personal
        else -> Category.Personal
    }

    @TypeConverter
    fun priorityToString(priority: Priority): String = when (priority) {
        Priority.High -> "HIGH"
        Priority.Medium -> "MEDIUM"
        Priority.Low -> "LOW"
    }

    @TypeConverter
    fun stringToPriority(value: String): Priority = when (value) {
        "HIGH" -> Priority.High
        "MEDIUM" -> Priority.Medium
        "LOW" -> Priority.Low
        else -> Priority.Low
    }
}
