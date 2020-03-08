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
        scope.launch { updateAndNotify() }
    }

    private suspend fun updateAndNotify() {
        withContext(Dispatchers.Main) {
            val firstVisibleView = max(taskAdapter.getPositionOfFirstVisibleTaskView(), 0)
            val numberOfVisibleViews = taskAdapter.getNumberOfVisibleTaskViews()
            async {
                updateTimeToDueDateCache(firstVisibleView, numberOfVisibleViews)
            }.invokeOnCompletion {
                taskAdapter.notifyItemRangeChanged(firstVisibleView, numberOfVisibleViews, UPDATE_DUE_DATE_VIEW)
            }
            delay(delayMillis)
            updateAndNotify()
        }
    }

    private suspend fun updateTimeToDueDateCache(firstVisibleView: Int, numberOfVisibleViews: Int) {
        withContext(Dispatchers.Default) {
            val updatedTimes = mutableMapOf<Task, String>()
            val lastVisibleView = firstVisibleView + numberOfVisibleViews
            for (i in firstVisibleView until lastVisibleView) {
                val task = tasks[i]
                updatedTimes[task] = timeToDueDateFormatter.formatteDueDate(task)
            }
            timeToDueDateCache.putAll(updatedTimes)
        }
    }

}