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
import androidx.compose.ui.Modifier
import com.tc1412.reminder.Priority
import com.tc1412.reminder.allPriorities

@Composable
fun PriorityPicker(priority: Priority?, onSelect: (Priority) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Button({expanded = true}) {
        Text(priority?.name ?: "Seleccionar...")
        DropdownMenu(expanded, {expanded = false}) {
            allPriorities.forEach {
                DropdownMenuItem({Text(it.name)}, {onSelect(it); expanded = false;})
            }
        }
    }
}