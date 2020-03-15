package de.anew.activities.tasks

import de.anew.activities.tasks.TaskAdapter.TaskViewProperties
import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.models.task.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.max
import kotlin.math.min

class PeriodicViewUpdater(private val taskAdapter: TaskAdapter, private val updateMutex: Mutex) {

    private val delayMillis = 1000L

    private var immediatelyUpdated = false

    fun schedule(scope: CoroutineScope) {
        scope.launch(Dispatchers.Main) {
            while (isActive) {
                if (!immediatelyUpdated) {
                    updateAndNotify()
                } else {
                    immediatelyUpdated = false
                }
                delay(delayMillis)
            }
        }
    }

    fun updateImmediately(scope: CoroutineScope) {
        scope.launch(Dispatchers.Main) {
            immediatelyUpdated = true
            updateAndNotify()
        }
    }

    private suspend fun updateAndNotify() {
        updateMutex.withLock {
            val firstVisibleView = max(taskAdapter.getPositionOfFirstVisibleTaskView(), 0)
            val numberOfVisibleViews = taskAdapter.getNumberOfVisibleTaskViews()
            withContext(Dispatchers.Default) {
                updateTaskPropertyCache(firstVisibleView, numberOfVisibleViews)
            }
            taskAdapter.notifyItemRangeChanged(firstVisibleView, numberOfVisibleViews, UPDATE_DUE_DATE_VIEW)
        }
    }

    private fun updateTaskPropertyCache(firstVisibleView: Int, numberOfVisibleViews: Int) {
        val updatedCache = mutableMapOf<Task, TaskViewProperties>()
        val tasks = taskAdapter.getTasks()
        val firstTaskIndex = max(firstVisibleView, 0)
        val lastTaskIndex = min(firstVisibleView + numberOfVisibleViews - 1, tasks.size - 1)
        for (taskIndex in firstTaskIndex.rangeTo(lastTaskIndex)) {
            val task = tasks[taskIndex]
            val isFulfilled = task.isFulfilled()
            val dueIn = task.dueIn()
            val timeToDueDate = taskAdapter.formatDueDate(task.period, dueIn)
            val fontColor = taskAdapter.getTaskFontColor(isFulfilled, dueIn)
            val backgroundColor = taskAdapter.getTaskBackgroundColor(isFulfilled, dueIn)
            updatedCache[task] = TaskViewProperties(timeToDueDate, fontColor, backgroundColor)
        }
        taskAdapter.updateTaskPropertyCache(updatedCache)
    }

}