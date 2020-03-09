package de.anew.activities.tasks

import androidx.recyclerview.widget.DiffUtil.Callback
import de.anew.models.task.Task

class TasksDiffCallback(
    private val oldTasks: List<Task>,
    private val newTasks: List<Task>
) : Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition].id == newTasks[newItemPosition].id
    }

    override fun getOldListSize() = oldTasks.size

    override fun getNewListSize() = newTasks.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition].name == newTasks[newItemPosition].name
    }

}
