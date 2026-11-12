package com.tc1412.reminder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.customcomps.ReminderCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderList() {
    Scaffold(topBar = { TopAppBar({ Text("Recordatorios") }) }) { ip ->
        Row(Modifier.padding(ip).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator()
            Text("Cargando...")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderList(rem: List<Reminder>, onCreate: () -> Unit, onReminder: (Int) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = {Text("Recordatorios")}) }, floatingActionButton = { FloatingActionButton(onCreate) { Text("+") } }) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            itemsIndexed(rem) { index, item ->
                val idx = index
                ReminderCard(item, { onReminder(idx) })
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListError(message: String, onRetry: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = {Text("Recordatorios - Error")}) }, floatingActionButton = { FloatingActionButton(onRetry) { Text("Reintentar") } }) { innerPadding ->
        Text(message, Modifier.padding(innerPadding))
    }
}