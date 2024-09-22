package com.pwlimaverde.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pwlimaverde.todolist.ui.screens.addedit.AddEditScreen
import com.pwlimaverde.todolist.ui.screens.list.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class AddEtiteRoute(val id: Long? = null)

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListRoute
    ) {
        composable<ListRoute> {
            ListScreen(
                navigateToAddEditScreen = {id ->
                    navController.navigate(AddEtiteRoute(id))
                },
            )
        }
        composable<AddEtiteRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEtiteRoute>()
            AddEditScreen(
                id = addEditRoute.id,
                navigateBack = {
                    navController.popBackStack()
                }
            )

        }
    }
}