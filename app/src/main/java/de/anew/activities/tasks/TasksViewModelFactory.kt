package de.anew.activities.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.anew.models.TasksRepository

class TasksViewModelFactory(private val tasksRepository: TasksRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TasksRepository::class.java).newInstance(tasksRepository)
    }

}