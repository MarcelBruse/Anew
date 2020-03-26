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