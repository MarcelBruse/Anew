package de.anew.activities.tasks

import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.activities.tasks.TaskAdapter.TaskViewProperties
import de.anew.models.task.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

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
            val timeToDueDate = taskAdapter.formatDueDate(task.period, dueIn)
            val fontColor = taskAdapter.getTaskFontColor(isFulfilled, dueIn)
            updatedCache[task] = TaskViewProperties(timeToDueDate, fontColor)
        }
        taskAdapter.updateTaskPropertyCache(updatedCache)
    }

}
