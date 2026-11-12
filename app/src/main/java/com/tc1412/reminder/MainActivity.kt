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
import com.tc1412.reminder.repositories.ReminderRepository
import com.tc1412.reminder.repositories.TestRepository
import com.tc1412.reminder.ui.theme.ReminderTheme
import com.tc1412.reminder.viewmodels.ReminderList
import java.time.LocalDateTime
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