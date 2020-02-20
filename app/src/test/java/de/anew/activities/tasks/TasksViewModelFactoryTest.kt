package de.anew.activities.tasks

import androidx.lifecycle.LiveData
import de.anew.models.task.Task
import de.anew.models.task.TasksRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TasksViewModelFactoryTest {

    @Test
    fun viewModelCreation() {
        val tasksRepository = mockk<TasksRepository>()
        val liveData = mockk<LiveData<List<Task>>>()
        every { tasksRepository.getAllTasks() } returns liveData
        val tasksViewModel = TasksViewModelFactory(tasksRepository).create(TasksViewModel::class.java)
        assertThat(tasksViewModel).isNotNull
        verify { tasksRepository.getAllTasks() }
    }

}