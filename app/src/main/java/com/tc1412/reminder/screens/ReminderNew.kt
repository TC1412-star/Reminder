package com.tc1412.reminder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tc1412.reminder.Category
import com.tc1412.reminder.Priority
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.reminders
import java.time.LocalDateTime
import java.time.Month

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderNew(onCreate : (Reminder) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var day by rememberSaveable { mutableStateOf("01") }
    var month by rememberSaveable { mutableStateOf("01") }
    var year by rememberSaveable { mutableStateOf("2027") }
    var hour by rememberSaveable { mutableStateOf("12") }
    var minute by rememberSaveable { mutableStateOf("30") }
    Scaffold(topBar = { TopAppBar({Text("Nuevo recordatorio")}) }) { innerPadding ->
        Card(Modifier.padding(innerPadding)) {
            Column {
                Text("Nombre:")
                TextField(name, { txt -> name = txt }, Modifier.fillMaxWidth(), placeholder = { Text("Nombre del recordatorio") })

                Text("Descripción:")
                TextField(description, { txt -> description = txt }, Modifier.fillMaxWidth(), placeholder = { Text("Descripción del recordatorio") })

                Text("Fecha:")
                Row(Modifier.fillMaxWidth()) {
                    TextField(day, { txt -> day = txt }, placeholder = { Text("Día") }, label = { Text("Día") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                    TextField(month, { txt -> month = txt }, placeholder = { Text("Mes") }, label = { Text("Mes") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                    TextField(year, { txt -> year = txt }, placeholder = { Text("Año") }, label = { Text("Año") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                }

                Text("Hora:")
                Row(Modifier.fillMaxWidth()) {
                    TextField(hour, { txt -> hour = txt }, placeholder = { Text("Día") }, label = { Text("Día") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                    TextField(minute, { txt -> minute = txt }, placeholder = { Text("Mes") }, label = { Text("Mes") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                }

                val trimmedName = name.trim()
                val Y = year.toIntOrNull()
                val M = month.toIntOrNull()
                val D = day.toIntOrNull()
                val h = hour.toIntOrNull()
                val m = minute.toIntOrNull()
                val validateName = trimmedName.length >= 4
                val validateDate = Y != null
                        && M?.let { x -> x in 1..12 } ?: false
                        && D?.let { x -> x in 1..Month.of(M).length(Y % 4 == 0) } ?: false
                val validateTime = h?.let { x -> x in 0 .. 23 } ?: false
                        && m?.let { x -> x in 0 .. 59 } ?: false
                Button({
                    val rem = Reminder(0, trimmedName, description, LocalDateTime.of(Y!!, M!!, D!!, h!!, m!!), Category.Social, Priority.Low)
                    onCreate(rem)
                }, enabled = validateName && validateDate && validateTime) { Text("Guardar") }
            }
        }
    }
}