package com.tc1412.reminder.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.tc1412.reminder.FormatTime
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.ShortFormatTime
import com.tc1412.reminder.reminders

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetails(reminder: Reminder, onRemove: () -> Unit) {
    Scaffold(topBar = { TopAppBar({Text(reminder.name)}) }) { inPad ->
        Column(Modifier.padding(inPad)) {
            Text(reminder.name, Modifier.fillMaxWidth(), fontSize = 24.sp, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline, textAlign = TextAlign.Center)
            Text(reminder.description, fontStyle = FontStyle.Italic)
            Text("Establecido para el ${FormatTime(reminder.time)}.")
            Text("Categoría: ${reminder.category.name} | Prioridad: ${reminder.priority.name}")
            Button(onRemove, Modifier.fillMaxWidth()) { Text("Eliminar") }
        }
    }
}