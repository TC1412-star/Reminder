package com.tc1412.reminder.repositories

import com.tc1412.reminder.Reminder

interface ReminderRepository {
    suspend fun getReminders() : List<Reminder>
    suspend fun storeReminder(rem:Reminder)
    suspend fun removeReminder(idx:Int)
}