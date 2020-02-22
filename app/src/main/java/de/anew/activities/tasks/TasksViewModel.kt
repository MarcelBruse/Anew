package de.anew.activities.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.anew.models.task.Task
import de.anew.models.task.TasksRepository
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    private val unsortedTasks: LiveData<List<Task>> = tasksRepository.getAllTasks()

    val tasks: LiveData<List<Task>>

    init {
        val sortedTasks = MediatorLiveData<List<Task>>()
        sortedTasks.addSource(unsortedTasks) { taskList ->
            taskList?.let {
                sortedTasks.value = it.sortedWith(TimeToDueDateComparator())
            }
        }
        tasks = sortedTasks
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        tasksRepository.deleteTask(task)
    }

}