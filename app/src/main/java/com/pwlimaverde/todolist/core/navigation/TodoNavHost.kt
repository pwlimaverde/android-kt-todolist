package com.pwlimaverde.todolist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pwlimaverde.todolist.core.models.todo1
import com.pwlimaverde.todolist.core.models.todo2
import com.pwlimaverde.todolist.core.models.todo3
import com.pwlimaverde.todolist.ui.screens.AddEditScreen
import com.pwlimaverde.todolist.ui.screens.ListScreen
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
                todo = listOf(
                    todo1,
                    todo2,
                    todo3
                )
            )
        }
        composable<AddEtiteRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEtiteRoute>()
            AddEditScreen()

        }
    }
}