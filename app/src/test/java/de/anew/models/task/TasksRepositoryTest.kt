package de.anew.models.task

import androidx.lifecycle.MutableLiveData
import de.anew.persistency.TaskDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TasksRepositoryTest {

    @Test
    fun getTask() {
        val taskDao = mockk<TaskDao>()
        val liveData = MutableLiveData<Task>()
        val taskId = 42L
        every { taskDao.load(taskId) } returns liveData
        val tasksRepository = TasksRepository(taskDao)
        val task = tasksRepository.getTask(taskId)
        assertThat(task).isEqualTo(liveData)
    }

    @Test
    fun getAllTasks() {
        val taskDao = mockk<TaskDao>()
        val liveData = MutableLiveData<List<Task>>()
        every { taskDao.loadAll() } returns liveData
        val tasksRepository = TasksRepository(taskDao)
        val tasks = tasksRepository.getAllTasks()
        assertThat(tasks).isEqualTo(liveData)
    }

    @Test
    fun saveTask() {
        val taskDao = mockk<TaskDao>()
        val taskToBeSaved = mockk<Task>()
        every { taskDao.save(taskToBeSaved) } returns Unit
        val tasksRepository = TasksRepository(taskDao)
        runBlocking {
            tasksRepository.saveTask(taskToBeSaved)
        }
        verify { taskDao.save(taskToBeSaved) }
    }

    @Test
    fun deleteTask() {
        val taskDao = mockk<TaskDao>()
        val taskToBeDeleted = mockk<Task>()
        every { taskDao.delete(taskToBeDeleted) } returns Unit
        val tasksRepository = TasksRepository(taskDao)
        runBlocking {
            tasksRepository.deleteTask(taskToBeDeleted)
        }
        verify { taskDao.delete(taskToBeDeleted) }
    }

}