package com.tc1412.reminder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
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
fun ReminderList(rem: List<Reminder>, isError: Boolean, onCreate: () -> Unit, onReminder: (Long) -> Unit, onSettings: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Recordatorios") }, actions = { TextButton(onClick = onSettings) { Text("Configuración") } }) }, floatingActionButton = { FloatingActionButton(onCreate) { Text("+") } }) { innerPadding ->
        Column(Modifier.padding(innerPadding).fillMaxWidth()) {
            if(isError)
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("Ocurrió un error en el servicio remoto. Se muestran los recordatorios almacenados localmente.")
                }
            if(rem.isEmpty())
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("No hay recordatorios actualmente.")
                }
            else
                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    itemsIndexed(rem) { index, item ->
                        ReminderCard(item, { onReminder(item.id) })
                    }
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