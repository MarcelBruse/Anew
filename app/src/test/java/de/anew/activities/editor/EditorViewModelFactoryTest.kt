package de.anew.activities.editor

import androidx.lifecycle.LiveData
import de.anew.models.task.Task
import de.anew.models.task.TasksRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EditorViewModelFactoryTest {

    @Test
    fun viewModelCreation() {
        val tasksRepository = mockk<TasksRepository>()
        val liveData = mockk<LiveData<Task>>()
        val taskId = 42L
        every { tasksRepository.getTask(taskId) } returns liveData
        val editorViewModelFactory = EditorViewModelFactory(tasksRepository, taskId)
        assertThat(editorViewModelFactory).isNotNull
        val editorViewModel = editorViewModelFactory.create(EditorViewModel::class.java)
        assertThat(editorViewModel).isNotNull
        verify { tasksRepository.getTask(taskId) }
    }

}