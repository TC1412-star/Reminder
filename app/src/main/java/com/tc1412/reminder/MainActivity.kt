package com.tc1412.reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(applicationContext)
        }
    }
}
fun FormatTime(time: LocalDateTime) : String {
    return "${time.dayOfMonth.toString().padStart(2, '0')}/${time.month.value.toString().padStart(2, '0')}/${time.year} a las ${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}hs"
}
fun ShortFormatTime(time: LocalDateTime) : String {
    return "${time.dayOfMonth.toString().padStart(2, '0')}/${time.month.value.toString().padStart(2, '0')}/${time.year} ${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
}