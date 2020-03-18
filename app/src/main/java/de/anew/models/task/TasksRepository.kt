package de.anew.models.task

import de.anew.persistency.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksRepository(private val taskDao: TaskDao) {

    fun getTask(taskId: Long) = taskDao.load(taskId)

    fun getAllTasks() = taskDao.loadAll()

    suspend fun saveTask(task: Task) = withContext(Dispatchers.IO) {
        launch { taskDao.save(task) }
    }

    suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO) {
        launch { taskDao.delete(task) }
    }

}