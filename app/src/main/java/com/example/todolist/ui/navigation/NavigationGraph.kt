package com.example.todolist.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.data.local.TaskDatabase
import com.example.todolist.data.repository.TaskRepository
import com.example.todolist.ui.TaskDetailScreen
import com.example.todolist.ui.TaskScreen
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.viewmodel.ViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val context = LocalContext.current

    // 1. Initialisation de la Base de données
    val database = remember { TaskDatabase.getDatabase(context) }

    // 2. Initialisation du Repository
    val repository = remember { TaskRepository(database.taskDao()) }

    // 3. Initialisation du ViewModel (avec la Factory pour lui passer le Repository)
    val viewModel: TaskViewModel = viewModel(factory = ViewModelFactory(repository))

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        // Écran Principal (Liste des tâches)
        composable("home") {
            // On passe le ViewModel existant
            TaskScreen(navController = navController, viewModel = viewModel)
        }

        // Écran de Détail (TP)
        composable("task_detail/{taskId}") { backStackEntry ->
            // On extrait l'ID de l'URL de navigation
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()

            taskId?.let {
                // On passe le MÊME ViewModel à l'écran de détail
                TaskDetailScreen(navController = navController, taskId = it, viewModel = viewModel)
            }
        }
    }
}