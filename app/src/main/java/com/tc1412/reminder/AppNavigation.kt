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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            when(appStatus) {
                is AppStatus.Loading -> ReminderList()
                is AppStatus.Error -> ReminderListError((appStatus as AppStatus.Error).message, { /*System.out.print("Reintentando... (simulado)")*/appStatus = AppStatus.Loading })
                is AppStatus.Ready -> ReminderList(
                    (appStatus as AppStatus.Ready).data,
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
            ReminderNew(onCreate = { reminder ->
                if(appStatus is AppStatus.Ready)
                    (appStatus as AppStatus.Ready).data.add(reminder)
                navController.popBackStack()
            })
        }
        composable("details/{id}", listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            if(appStatus !is AppStatus.Ready || id !in 0 .. (appStatus as AppStatus.Ready).data.size)
                Text("Invalid ID.")
            else
                ReminderDetails(
                    remember { (appStatus as AppStatus.Ready).data[id] },
                    onRemove = {
                        (appStatus as AppStatus.Ready).data.removeAt(id)
                        navController.popBackStack()
                    }
                )
        }
    }
}