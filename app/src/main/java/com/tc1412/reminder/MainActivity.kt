package com.tc1412.reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tc1412.reminder.ui.theme.ReminderTheme
import java.time.LocalDateTime
val reminders : List<Reminder> = listOf(
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
)
var appStatus: AppStatus by mutableStateOf(
    AppStatus.Ready(mutableStateListOf<Reminder>().apply { addAll(reminders) })
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}
fun FormatTime(time: LocalDateTime) : String {
    return "${time.dayOfMonth.toString().padStart(2, '0')}/${time.month.value.toString().padStart(2, '0')}/${time.year} a las ${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}hs"
}
fun ShortFormatTime(time: LocalDateTime) : String {
    return "${time.dayOfMonth.toString().padStart(2, '0')}/${time.month.value.toString().padStart(2, '0')}/${time.year} ${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
}