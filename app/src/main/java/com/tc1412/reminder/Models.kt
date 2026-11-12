package com.tc1412.reminder

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.time.LocalDateTime

sealed class Category(val name: String) {
    object Work : Category("Trabajo")
    object Social : Category("Social")
    object Personal : Category("Personal")
}
val allCategories = listOf(Category.Work, Category.Social, Category.Personal)
sealed class Priority(val name: String) {
    object High : Priority("Alta")
    object Medium : Priority("Media")
    object Low : Priority("Baja")
}
val allPriorities = listOf(Priority.Low, Priority.Medium, Priority.High)
sealed class AppStatus {
    object Loading : AppStatus()
    data class Ready(val data: List<Reminder>) : AppStatus()
    data class Error(val message:String) : AppStatus()
}
sealed class NewReminderStatus {
    object Creating : NewReminderStatus()
    object Sending : NewReminderStatus()
    object Sent : NewReminderStatus()
    data class Error(val message:String) : NewReminderStatus()
}
data class NewReminderState(
    val title: String = "",
    val description: String = "",
    val category: Category? = null,
    val priority: Priority? = null,
    val dateTime: LocalDateTime = LocalDateTime.now()
)
data class Reminder(
    val id : Int,
    var name : String,
    var description : String,
    var time : LocalDateTime,
    var category: Category,
    var priority: Priority
)