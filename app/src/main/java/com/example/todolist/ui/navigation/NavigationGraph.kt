package com.example.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.TaskDetailScreen
import com.example.todolist.ui.TaskScreen

@Composable
    fun NavigationGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "Accueil"){
            composable("Accueil"){
                TaskScreen(navController)
            }
            composable("task_detail/{taskId}"){
                backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                taskId?.let {
                    TaskDetailScreen(navController, taskId)
                }

            }
    }
}