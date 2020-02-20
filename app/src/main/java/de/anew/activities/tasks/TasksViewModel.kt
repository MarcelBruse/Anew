package de.anew.activities.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.anew.models.task.Task
import de.anew.models.task.TasksRepository
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    val tasks: LiveData<List<Task>> = tasksRepository.getAllTasks()

    fun deleteTask(task: Task) = viewModelScope.launch {
        tasksRepository.deleteTask(task)
    }

}