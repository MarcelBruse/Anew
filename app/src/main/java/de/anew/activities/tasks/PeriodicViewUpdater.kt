package de.anew.activities.tasks

import de.anew.activities.tasks.TaskAdapter.CachedTaskProperties
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
    private val taskPropertiesCache: MutableMap<Task, CachedTaskProperties>,
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
        val updatedCache = mutableMapOf<Task, CachedTaskProperties>()
        val firstTaskIndex = max(firstVisibleView, 0)
        val lastTaskIndex = min(firstVisibleView + numberOfVisibleViews - 1, tasks.size - 1)
        for (taskIndex in firstTaskIndex.rangeTo(lastTaskIndex)) {
            val task = tasks[taskIndex]
            val dueIn = task.dueIn()
            val timeToDueDate = timeToDueDateFormatter.formatDueDate(task.period, dueIn)
            val backgroundColor = TaskColorizer.getBackgroundColor(task.isFulfilled(), dueIn)
            updatedCache[task] = CachedTaskProperties(timeToDueDate, backgroundColor)
        }
        taskPropertiesCache.putAll(updatedCache)
    }

}