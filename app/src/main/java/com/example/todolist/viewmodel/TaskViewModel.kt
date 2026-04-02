package com.example.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todolist.data.*
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()
    val tasks : StateFlow<List<Task>> = repository.tasks

    fun addTask(name: String) {
        repository.addTask(name)
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
    }


}