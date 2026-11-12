package com.tc1412.reminder.customcomps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.ShortFormatTime

@Composable
fun ReminderCard(reminder: Reminder, onSelect: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onSelect, modifier = modifier.fillMaxWidth()) {
        Column {
            Text(reminder.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline)
            Text("Categoría: ${reminder.category.name}")
            Text("Prioridad: ${reminder.priority.name}")
        }
    }
}