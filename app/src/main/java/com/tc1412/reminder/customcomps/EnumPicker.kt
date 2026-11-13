package com.tc1412.reminder.customcomps

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tc1412.reminder.allPriorities

@Composable
fun <T> EnumPicker(value: T?, values: List<T>, onSelect: (T) -> Unit, nameFor: (value: T, selected: Boolean) -> String) {
    var expanded by remember { mutableStateOf(false) }
    Button({expanded = true}) {
        Text(value?.let { nameFor(it, true) } ?: "Seleccionar...")
        DropdownMenu(expanded, {expanded = false}) {
            values.forEach {
                DropdownMenuItem({Text(nameFor(it, false))}, {onSelect(it); expanded = false;})
            }
        }
    }
}