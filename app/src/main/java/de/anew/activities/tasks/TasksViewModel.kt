package de.anew.activities.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.anew.models.task.Task
import de.anew.models.task.TaskValidator
import de.anew.models.task.TasksRepository
import kotlinx.coroutines.launch
import org.threeten.bp.ZonedDateTime

class TasksViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    private val unsortedTasks: LiveData<List<Task>> = tasksRepository.getAllTasks()

    val tasks: LiveData<List<Task>>

    init {
        val sortedTasks = MediatorLiveData<List<Task>>()
        sortedTasks.addSource(unsortedTasks) { taskList ->
            taskList?.let {
                sortedTasks.value = it.sortedWith(TimeToDueDateComparator())
            }
        }
        tasks = sortedTasks
    }

    fun getNumberOfTasks() = tasks.value?.size ?: 0

    fun markTaskAsFulfilled(task: Task) = viewModelScope.launch {
        val fulfilledNow = ZonedDateTime.now()
        if (validateFulfillmentTime(task, fulfilledNow)) {
            task.lastFulfillmentTime = fulfilledNow
            tasksRepository.saveTask(task)
        }
    }

    private fun validateFulfillmentTime(task: Task, lastFulfillmentTimeCandidate: ZonedDateTime): Boolean {
        return TaskValidator.validateLastFulfilmentTime(task, lastFulfillmentTimeCandidate).isEmpty()
    }

}