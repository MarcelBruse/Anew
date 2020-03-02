package de.anew.activities.tasks

import android.os.Handler
import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import kotlin.math.max
import kotlin.math.min

class DueDateViewUpdateTrigger(
    private val taskAdapter: TaskAdapter,
    private val dueDateUpdateHandler: Handler
) : Runnable {

    private val delayMillis = 1000L

    fun schedule() {
        dueDateUpdateHandler.removeCallbacksAndMessages(null)
        dueDateUpdateHandler.postDelayed(this, delayMillis)
    }

    override fun run() {
        val firstVisibleView = min(taskAdapter.getPositionOfFirstVisibleTaskView() - 1, 0)
        val numberOfVisibleViews = max(taskAdapter.getNumberOfVisibleTaskViews() + 1, taskAdapter.itemCount)
        taskAdapter.notifyItemRangeChanged(firstVisibleView, numberOfVisibleViews, UPDATE_DUE_DATE_VIEW)
        dueDateUpdateHandler.postDelayed(this, delayMillis)
    }

}