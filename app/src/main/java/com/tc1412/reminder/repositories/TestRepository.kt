package com.tc1412.reminder.repositories

import com.tc1412.reminder.Category
import com.tc1412.reminder.Priority
import com.tc1412.reminder.Reminder
import kotlinx.coroutines.delay
import java.time.LocalDateTime

class TestRepository : ReminderRepository {
    private val reminders = ArrayList(listOf(
        Reminder(
            id = 2,
            name = "Another test",
            description = "Esto es una prueba de un recordatorio.",
            time = LocalDateTime.of(
                2026, 11, 5,
                15, 0, 0
            ),
            category = Category.Social,
            priority = Priority.Medium
        ),
        Reminder(
            id = 3,
            name = "Otra prueba",
            description = "Esto es un recordatorio simulado :)",
            time = LocalDateTime.of(
                2026, 12, 14,
                18, 45, 0
            ),
            category = Category.Personal,
            priority = Priority.Low
        ),
        Reminder(
            id = 1,
            name = "Test",
            description = "Just a test reminder :)",
            time = LocalDateTime.of(
                2026, 10, 29,
                14, 30, 0
            ),
            category = Category.Work,
            priority = Priority.High
        )
    ))
    var simulatingError = false
    override suspend fun getReminders(): List<Reminder> {
        delay(1000)

        if(simulatingError) {
            simulatingError = false
            throw Exception("Test error :(")
        }
        return reminders
    }

    override suspend fun storeReminder(rem: Reminder) {
        delay(400)
        simulatingError = true
        reminders.add(rem)
    }

    override suspend fun removeReminder(idx: Int) {
        delay(1500)
        reminders.removeAt(idx)
    }
}