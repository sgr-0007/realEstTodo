package com.team2.todo.utils

/**
 * Created by Manu KJ on 11/1/23.
 */

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.team2.todo.screens.listing.MainScreen
import com.team2.todo.screens.add_todo.AddTodos
import com.team2.todo.screens.details_page.DetailsPage
import com.team2.todo.screens.pre_defined_sub_task.PreDefinedSubTask
import com.team2.todo.screens.subtodo_details.SubTodoDetails

// Enum of all the Screen
enum class Screen {
    MainScreen, AddTodos, AddOrEditSubToDo, DetailsScreen, SubTodoDetails, EditTodo,EditSubTodo, PreDefinedSubTask
}

object NavigationUtil {
    lateinit var navController: NavHostController

    // initializing the nav controller before using it
    fun init(navController: NavHostController) {
        this.navController = navController;
    }

    // navigate to the given Enum
    fun navigateTo(screen: Screen) {
        navController.navigate(screen.name)
    }

    fun navigateTo(screen: String) {
        navController.navigate(screen)
    }

    fun goBack() {
        navController.popBackStack();
    }
}


@Composable
fun NavHostControllerProvider() {
    NavHost(
        navController = NavigationUtil.navController,
        startDestination = Screen.MainScreen.name
    ) {
        composable(Screen.MainScreen.name) { MainScreen() }
        composable(Screen.AddTodos.name) { AddTodos() }
        composable(
            route = "${Screen.DetailsScreen.name}/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1
            DetailsPage(todoId)
        }
        composable(
            route = "${Screen.SubTodoDetails.name}/{subTodoId}",
            arguments = listOf(navArgument("subTodoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val subTodoId = backStackEntry.arguments?.getLong("subTodoId") ?: -1
            SubTodoDetails(subTodoId)
        }
        composable(
            route = "${Screen.AddOrEditSubToDo.name}/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1
            AddTodos(isSubTodo = true, todoid = todoId)
        }

        composable(
            route = "${Screen.EditTodo.name}/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1
            Log.d("In Edit Route", "In edit route")
            AddTodos(isSubTodo = false, todoid = todoId, isEdit = true)
        }

        composable(
            route = "${Screen.PreDefinedSubTask.name}/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) { backStackEntry ->
            Log.d("In Edit Route", "In  PreDefinedSubTask Route")
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1
            PreDefinedSubTask(todoId = todoId)
        }

        composable(
            route = "${Screen.EditSubTodo.name}/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1
            Log.d("In Edit Route","In edit route")
            AddTodos(isSubTodo = true, todoid = todoId, isEditSubTodo = true)
        }
    }
}