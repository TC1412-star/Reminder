package com.tc1412.reminder

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastJoinToString
import com.tc1412.reminder.ui.theme.ReminderTheme
import java.sql.Date
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReminderTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text("=[ REMINDER ]=\n\n" + reminders.sortedBy { reminder -> reminder.id }.map { reminder -> "Recordatorio ${reminder.id}: ${reminder.name}\n" +
                            "\t${reminder.description}\n" +
                            "\tPara el ${reminder.time.dayOfMonth.toString().padStart(2, '0')}/${reminder.time.month.value.toString().padStart(2, '0')}/${reminder.time.year} a las ${reminder.time.hour.toString().padStart(2, '0')}:${reminder.time.minute.toString().padStart(2, '0')}hs\n" +
                            "\tCategoría: ${reminder.category.name}\n" +
                            "\tPrioridad: ${reminder.priority.name}\n"
                    }.fastJoinToString(""), Modifier.padding(innerPadding))
                }
            }
        }
    }
}