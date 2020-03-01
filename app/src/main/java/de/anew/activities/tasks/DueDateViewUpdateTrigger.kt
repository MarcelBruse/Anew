package de.anew.activities.tasks

import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW

class DueDateViewUpdateTrigger(
    private val adapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>,
    private val dueDateUpdateHandler: Handler
) : Runnable {

    private val delayMillis = 1000L

    fun schedule() {
        dueDateUpdateHandler.removeCallbacks(this)
        dueDateUpdateHandler.postDelayed(this, delayMillis)
    }

    override fun run() {
        adapter.notifyItemRangeChanged(0, adapter.itemCount, UPDATE_DUE_DATE_VIEW)
        dueDateUpdateHandler.postDelayed(this, delayMillis)
    }

}