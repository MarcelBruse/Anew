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