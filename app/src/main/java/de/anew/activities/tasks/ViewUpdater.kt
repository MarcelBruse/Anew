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

import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.activities.tasks.TaskAdapter.TaskViewProperties
import de.anew.models.task.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class ViewUpdater(
    private val taskAdapter: TaskAdapter,
    private val updateMutex: Mutex
) {

    fun update(scope: CoroutineScope, completionCallback: () -> Unit) {
        scope.launch(Dispatchers.Main) {
            updateMutex.withLock {
                withContext(Dispatchers.Default) {
                    updateTaskPropertyCache()
                }
                taskAdapter.notifyItemRangeChanged(0, taskAdapter.itemCount, UPDATE_DUE_DATE_VIEW)
                completionCallback.invoke()
            }
        }
    }

    private fun updateTaskPropertyCache() {
        val updatedCache = mutableMapOf<Task, TaskViewProperties>()
        for (task in taskAdapter.getTasks()) {
            val isFulfilled = task.isFulfilled()
            val dueIn = task.dueIn()
            val timeToDueDate = taskAdapter.formatDueDate(task.period, isFulfilled, dueIn)
            val fontColor = taskAdapter.getTaskFontColor(isFulfilled, dueIn)
            updatedCache[task] = TaskViewProperties(timeToDueDate, fontColor)
        }
        taskAdapter.updateTaskPropertyCache(updatedCache)
    }

}
