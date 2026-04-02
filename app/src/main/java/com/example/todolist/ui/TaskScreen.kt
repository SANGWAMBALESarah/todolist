package com.example.todolist.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todolist.data.Task
import com.example.todolist.viewmodel.TaskViewModel
@Composable
fun TaskItem(task: Task,onToggle:()->Unit,onClick:()->Unit) {
    Row (modifier=Modifier.fillMaxSize().padding(8.dp)){
        Checkbox(checked = task.isDone, onCheckedChange = { onToggle() })
        Text(text = task.name, modifier = Modifier.weight(1f).
        padding(start = 8.dp)
            .align(Alignment.CenterVertically)
            .clickable { onClick() }
        )

    }
}

@Composable
fun TaskScreen(navController: NavController, viewModel: TaskViewModel= viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(top=32.dp).padding(all = 16.dp)) {
        var taskName by remember { mutableStateOf("") }

        Row {
            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                modifier = Modifier.weight(1f).padding(16.dp),
                singleLine = true,
                label = { Text("Nom de la tâche") },
            )
            Button(onClick = {
                if (taskName.isNotBlank()) {
                    viewModel.addTask(taskName)
                    taskName = ""
                }
            }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Text("Ajouter")
            }
        }

        LazyColumn {
            items(tasks) { task ->
                TaskItem(task,
                    onToggle = {
                        viewModel.updateTask(task)
                    },
                    onClick = {navController.navigate("task_detail/${task.id}")}


                )
            }
        }
    }
}