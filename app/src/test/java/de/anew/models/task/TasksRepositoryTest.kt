/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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