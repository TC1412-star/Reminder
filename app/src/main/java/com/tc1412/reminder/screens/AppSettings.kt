package com.tc1412.reminder.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tc1412.reminder.preferences.Sort
import com.tc1412.reminder.preferences.UITheme
import com.tc1412.reminder.viewmodels.AppSettings as AppSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettings(status: AppSettingsViewModel, onBack: () -> Unit) {
    val preferences by status.userPreferences.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    TextButtonBack(onBack)
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Tema de la aplicación")
            UITheme.all.forEach { theme ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { status.setTheme(theme) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = preferences.uiTheme == theme,
                        onClick = { status.setTheme(theme) }
                    )
                    Text(theme.name)
                }
            }

            HorizontalDivider()

            Text("Orden de los recordatorios")
            Sort.all.forEach { sort ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { status.setSort(sort) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = preferences.sort == sort,
                        onClick = { status.setSort(sort) }
                    )
                    Text(sort.name)
                }
            }

            HorizontalDivider()

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Ocultar recordatorios pasados")
                }
                Switch(
                    checked = preferences.hidePast,
                    onCheckedChange = status::setHidePast
                )
            }
        }
    }
}

@Composable
private fun TextButtonBack(onBack: () -> Unit) {
    androidx.compose.material3.TextButton(onClick = onBack) {
        Text("Volver")
    }
}
