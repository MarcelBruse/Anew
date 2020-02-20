package de.anew.activities.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.anew.models.task.TasksRepository

class EditorViewModelFactory(
    private val tasksRepository: TasksRepository,
    private val taskId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val constructor = modelClass.getConstructor(TasksRepository::class.java, Long::class.java)
        return constructor.newInstance(tasksRepository, taskId)
    }

}