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
package de.anew.activities.editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.anew.R
import de.anew.lifecycle.SingleLiveEvent
import de.anew.models.task.TaskFactory
import de.anew.models.task.TaskValidator
import de.anew.models.task.TasksRepository
import de.anew.models.time.TimePeriod
import kotlinx.coroutines.launch

class EditorViewModel(private val tasksRepository: TasksRepository, taskId: Long) : ViewModel() {

    val task = if (taskId > 0) tasksRepository.getTask(taskId) else MutableLiveData(TaskFactory.newTask())

    val taskSavedOrDeletedEvent = SingleLiveEvent<Any>()

    fun saveTask(taskName: String, period: TimePeriod) {
        if (validateUserInput(taskName, period).isEmpty()) {
            task.value?.let {
                it.name = taskName.trim()
                it.period = period
                viewModelScope.launch {
                    tasksRepository.saveTask(it)
                    taskSavedOrDeletedEvent.call()
                }
            }
        }
    }

    fun validateUserInput(taskName: String, period: TimePeriod): List<Int> {
        val taskNameErrors = TaskValidator
            .validateTaskName(taskName)
            .map { getMessageIdForError(it) }
            .toList()
        val periodErrors = TaskValidator
            .validatePeriod(period)
            .map { getMessageIdForError(it) }
            .toList()
        return listOf(taskNameErrors, periodErrors).flatten()
    }

    private fun getMessageIdForError(error: TaskValidator.Error): Int {
        return when (error) {
            TaskValidator.Error.TASK_NAME_IS_BLANK -> R.string.blank_task_name
            TaskValidator.Error.TASK_NAME_LENGTH_EXCEEDS_LIMIT -> R.string.task_name_length
            TaskValidator.Error.UNDEFINED_PERIOD -> R.string.undefined_period
            else -> R.string.unknown_error
        }
    }

    fun deleteTask() {
        task.value?.let {
            viewModelScope.launch {
                tasksRepository.deleteTask(it)
                taskSavedOrDeletedEvent.call()
            }
        }
    }

}
