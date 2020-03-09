package de.anew.activities.tasks

import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.models.task.Task
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

class PeriodicViewUpdater(
    private val taskAdapter: TaskAdapter,
    private val tasks: List<Task>,
    private val timeToDueDateCache: ConcurrentHashMap<Task, String>,
    private val timeToDueDateFormatter: TimeToDueDateFormatter
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
        val firstVisibleView = max(taskAdapter.getPositionOfFirstVisibleTaskView(), 0)
        val numberOfVisibleViews = taskAdapter.getNumberOfVisibleTaskViews()
        withContext(Dispatchers.Default) {
            updateTimeToDueDateCache(firstVisibleView, numberOfVisibleViews)
        }
        taskAdapter.notifyItemRangeChanged(firstVisibleView, numberOfVisibleViews, UPDATE_DUE_DATE_VIEW)
    }

    private fun updateTimeToDueDateCache(firstVisibleView: Int, numberOfVisibleViews: Int) {
        val updatedTimes = mutableMapOf<Task, String>()
        val lastVisibleView = firstVisibleView + numberOfVisibleViews - 1
        for (taskIndex in firstVisibleView.rangeTo(lastVisibleView)) {
            val task = tasks[taskIndex]
            updatedTimes[task] = timeToDueDateFormatter.formatDueDate(task)
        }
        timeToDueDateCache.putAll(updatedTimes)
    }

}