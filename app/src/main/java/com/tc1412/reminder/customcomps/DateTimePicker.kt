package com.tc1412.reminder.customcomps

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import java.time.LocalDateTime
import java.time.Month
import kotlin.rem

@Composable
fun DateTimePicker(dateTime: LocalDateTime, onSet: (LocalDateTime) -> Unit, modifier: Modifier = Modifier) {
    var day by rememberSaveable { mutableStateOf(dateTime.dayOfMonth.toString().padStart(2, '0')) }
    var month by rememberSaveable { mutableStateOf(dateTime.monthValue.toString().padStart(2, '0')) }
    var year by rememberSaveable { mutableStateOf(dateTime.year.toString()) }
    var hour by rememberSaveable { mutableStateOf(dateTime.hour.toString().padStart(2, '0')) }
    var minute by rememberSaveable { mutableStateOf(dateTime.minute.toString().padStart(2, '0')) }

    val Y = year.toIntOrNull()
    val M = month.toIntOrNull()
    val D = day.toIntOrNull()
    val h = hour.toIntOrNull()
    val m = minute.toIntOrNull()
    val validateDate = Y != null
            && M?.let { x -> x in 1..12 } ?: false
            && D?.let { x -> x in 1..Month.of(M).length(Y % 4 == 0) } ?: false
    val validateTime = h?.let { x -> x in 0 .. 23 } ?: false
            && m?.let { x -> x in 0 .. 59 } ?: false

    Text("Fecha:")
    Row(Modifier.fillMaxWidth()) {
        TextField(day, { txt -> day = txt }, placeholder = { Text("Día") }, label = { Text("Día") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !validateDate, modifier = Modifier.weight(1f))
        TextField(month, { txt -> month = txt }, placeholder = { Text("Mes") }, label = { Text("Mes") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !validateDate, modifier = Modifier.weight(1f))
        TextField(year, { txt -> year = txt }, placeholder = { Text("Año") }, label = { Text("Año") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !validateDate, modifier = Modifier.weight(1f))
    }

    Text("Hora:")
    Row(Modifier.fillMaxWidth()) {
        TextField(hour, { txt -> hour = txt }, placeholder = { Text("Día") }, label = { Text("Día") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !validateTime, modifier = Modifier.weight(1f))
        TextField(minute, { txt -> minute = txt }, placeholder = { Text("Mes") }, label = { Text("Mes") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !validateTime, modifier = Modifier.weight(1f))
    }

    fun set() {
        if(validateDate && validateTime)
            onSet(LocalDateTime.of(Y, M, D, h, m))
    }
}