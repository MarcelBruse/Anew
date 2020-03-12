package de.anew.activities.tasks

import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.models.task.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.max
import kotlin.math.min

class PeriodicViewUpdater(
    private val taskAdapter: TaskAdapter,
    private val tasks: List<Task>,
    private val timeToDueDateCache: MutableMap<Task, String>,
    private val timeToDueDateFormatter: TimeToDueDateFormatter,
    private val updateView: Mutex
) {

    private val delayMillis = 1000L

    fun schedule(scope: CoroutineScope) {
        scope.launch(Dispatchers.Main) {
            while (isActive) {
                updateAndNotify()
                delay(delayMillis)
            }
        }
    }

    private suspend fun updateAndNotify() {
        updateView.withLock {
            val firstVisibleView = max(taskAdapter.getPositionOfFirstVisibleTaskView(), 0)
            val numberOfVisibleViews = taskAdapter.getNumberOfVisibleTaskViews()
            withContext(Dispatchers.Default) {
                updateTimeToDueDateCache(firstVisibleView, numberOfVisibleViews)
            }
            taskAdapter.notifyItemRangeChanged(firstVisibleView, numberOfVisibleViews, UPDATE_DUE_DATE_VIEW)
        }
    }

    private fun updateTimeToDueDateCache(firstVisibleView: Int, numberOfVisibleViews: Int) {
        val updatedTimes = mutableMapOf<Task, String>()
        val firstTaskIndex = max(firstVisibleView, 0)
        val lastTaskIndex = min(firstVisibleView + numberOfVisibleViews - 1, tasks.size - 1)
        for (taskIndex in firstTaskIndex.rangeTo(lastTaskIndex)) {
            val task = tasks[taskIndex]
            updatedTimes[task] = timeToDueDateFormatter.formatDueDate(task)
        }
        timeToDueDateCache.putAll(updatedTimes)
    }

}