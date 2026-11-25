package com.tc1412.reminder

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tc1412.reminder.database.createDatabase
import com.tc1412.reminder.preferences.DataStoring
import com.tc1412.reminder.preferences.Sort
import com.tc1412.reminder.preferences.UITheme
import com.tc1412.reminder.preferences.dataStore
import com.tc1412.reminder.remote.RemoteClient
import com.tc1412.reminder.repositories.DatabaseRepository
import com.tc1412.reminder.screens.AppSettings
import com.tc1412.reminder.screens.ReminderDetails
import com.tc1412.reminder.screens.ReminderList
import com.tc1412.reminder.screens.ReminderListError
import com.tc1412.reminder.screens.ReminderNew
import com.tc1412.reminder.ui.theme.ReminderTheme
import com.tc1412.reminder.viewmodels.AppSettings as AppSettingsViewModel
import com.tc1412.reminder.viewmodels.ReminderList as ReminderListView
import com.tc1412.reminder.viewmodels.ReminderNew as ReminderNewViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val database = remember(context) { createDatabase(context) }
    val repository = remember(database) { DatabaseRepository(database.remindersDao(), RemoteClient.service) }
    val reminderListState = remember(repository) { ReminderListView(repository) }
    val reminderNewState = remember(repository) { ReminderNewViewModel(repository) }
    val dataStoring = remember(context) { DataStoring(context.dataStore) }
    val settingsState = remember(dataStoring) { AppSettingsViewModel(dataStoring) }
    val preferences = settingsState.userPreferences.collectAsStateWithLifecycle().value
    val darkTheme = when (preferences.uiTheme) {
        UITheme.Dark -> true
        UITheme.Light -> false
        else -> isSystemInDarkTheme()
    }

    ReminderTheme(darkTheme = darkTheme) {
        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                when (val appStatus = reminderListState.appStatus.collectAsStateWithLifecycle().value) {
                    is AppStatus.Loading -> ReminderList()
                    is AppStatus.Error -> ReminderListError(
                        appStatus.message,
                        { reminderListState.loadReminders() }
                    )
                    is AppStatus.Ready -> {
                        val visibleReminders = if (preferences.hidePast) {
                            appStatus.data.filter { !it.dateTime.isBefore(LocalDateTime.now()) }
                        } else {
                            appStatus.data
                        }
                        val reminders = when (preferences.sort) {
                            Sort.Name -> visibleReminders.sortedBy { it.name.lowercase() }
                            Sort.DateTime -> visibleReminders.sortedBy { it.dateTime }
                            else -> visibleReminders
                        }
                        ReminderList(
                            reminders,
                            appStatus.databaseError,
                            onCreate = { navController.navigate("new") },
                            onReminder = { id -> navController.navigate("details/$id") },
                            onSettings = { navController.navigate("settings") }
                        )
                    }
                }
            }
            composable("settings") {
                AppSettings(
                    status = settingsState,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("new") {
                ReminderNew(
                    status = reminderNewState,
                    onSuccess = {
                        reminderListState.loadReminders()
                        navController.popBackStack()
                    }
                )
            }
            composable(
                "details/{id}",
                listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: -1L
                val appStatus = reminderListState.appStatus.collectAsState().value
                val reminder = (appStatus as? AppStatus.Ready)?.data?.firstOrNull { it.id == id }
                if (reminder == null) {
                    Text("Recordatorio no encontrado.")
                } else {
                    ReminderDetails(
                        reminder,
                        onRemove = { onError ->
                            reminderListState.removeReminder(
                                id = id,
                                onSuccess = {
                                    navController.popBackStack()
                                    reminderListState.loadReminders()
                                },
                                onError = onError
                            )
                        }
                    )
                }
            }
        }
    }
}
