package com.tc1412.reminder.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tc1412.reminder.Category
import com.tc1412.reminder.NewReminderState
import com.tc1412.reminder.NewReminderStatus
import com.tc1412.reminder.Priority
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.customcomps.CategoryPicker
import com.tc1412.reminder.customcomps.DateTimePicker
import com.tc1412.reminder.customcomps.PriorityPicker
import com.tc1412.reminder.viewmodels.ReminderNew
import java.time.LocalDateTime
import java.time.Month
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderNew(status: ReminderNew, onSuccess : () -> Unit) {
    Scaffold(topBar = { TopAppBar({Text("Nuevo recordatorio")}) }) { innerPadding ->
        Card(Modifier.padding(innerPadding)) {
            Column {
                Text("Nombre:")
                TextField(status.status.title, { status.setTitle(it) }, Modifier.fillMaxWidth(), placeholder = { Text("Nombre del recordatorio") }, isError = status.titleError, supportingText = { Text("El nombre debe tener al menos 4 caracteres, sin contar espacios iniciales ni finales.") })

                Text("Descripción:")
                TextField(status.status.description, { status.setDescription(it) }, Modifier.fillMaxWidth(), placeholder = { Text("Descripción del recordatorio") }, isError = status.descriptionError, supportingText = { Text("${status.status.description.length}/200 caracteres") })

                Text("Fecha y hora:")
                DateTimePicker(status.status.dateTime, {status.status})

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Categoría:")
                    CategoryPicker(status.status.category, {status.setCategory(it)})
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Prioridad:")
                    PriorityPicker(status.status.priority, {status.setPriority(it)})
                }

                val sending = status.state.collectAsStateWithLifecycle().value is NewReminderStatus.Sending
                Button({
                    status.send(onSuccess)
                }, enabled = !status.cannotSend && !sending, modifier = Modifier.fillMaxWidth()) {
                    /*Text(if(status) "Guardando..." else "Guardar")
                    if(sending)
                        CircularProgressIndicator()*/
                    if(sending) {
                        Text("Guardando...")
                        CircularProgressIndicator()
                    } else {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}