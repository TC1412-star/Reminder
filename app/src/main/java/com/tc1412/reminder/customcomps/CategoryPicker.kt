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
import com.tc1412.reminder.Category
import com.tc1412.reminder.Priority
import com.tc1412.reminder.allCategories
import com.tc1412.reminder.allPriorities

@Composable
fun CategoryPicker(category: Category?, onSelect: (Category) -> Unit, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Button({expanded = true}) {
        Text(category?.name ?: "Seleccionar...")
        DropdownMenu(expanded, {expanded = false}) {
            allCategories.forEach {
                DropdownMenuItem({Text(it.name)}, {onSelect(it); expanded = false;})
            }
        }
    }
}