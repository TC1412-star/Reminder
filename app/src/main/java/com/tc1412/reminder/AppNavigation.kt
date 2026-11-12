package com.tc1412.reminder

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tc1412.reminder.screens.ReminderDetails
import com.tc1412.reminder.screens.ReminderList
import com.tc1412.reminder.screens.ReminderListError
import com.tc1412.reminder.screens.ReminderNew
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tc1412.reminder.repositories.TestRepository
import com.tc1412.reminder.viewmodels.ReminderNew
import com.tc1412.reminder.viewmodels.ReminderList as ReminderListView

private val repository = TestRepository()
private val reminderListState = ReminderListView(repository)
private val reminderNewState = ReminderNew(repository)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            when(val appStatus = reminderListState.appStatus.collectAsStateWithLifecycle().value) {
                is AppStatus.Loading -> ReminderList()
                is AppStatus.Error -> ReminderListError(appStatus.message, { reminderListState.loadReminders() })
                is AppStatus.Ready -> ReminderList(
                    appStatus.data,
                    onCreate = {
                        navController.navigate("new")
                    },
                    onReminder = { id ->
                        navController.navigate("details/$id")
                    }
                )
            }
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
        composable("details/{id}", listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            val appStatus = reminderListState.appStatus.collectAsState().value
            if(appStatus !is AppStatus.Ready || id !in 0 .. appStatus.data.size)
                Text("Invalid ID.")
            else
                ReminderDetails(
                    remember { appStatus.data[id] },
                    onRemove = {
                        reminderListState.removeReminder(
                            idx = id,
                            onSuccess = {
                                navController.popBackStack()
                                reminderListState.loadReminders()
                            },
                            onError = it
                        )
                    }
                )
        }
    }
}