/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.anew.activities.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import de.anew.R
import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.activities.tasks.TaskAdapter.TaskViewHolder
import de.anew.models.task.Task
import de.anew.models.time.TimePeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.threeten.bp.Duration
import java.util.*

class TaskAdapter(
    private val tasksViewModel: TasksViewModel,
    private val taskItemClickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val taskNameViewPosition = 0

    private val dueDateViewPosition = 1

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewUpdater: ViewUpdater

    private val tasks = mutableListOf<Task>()

    private val taskPropertyCache = mutableMapOf<Task, TaskViewProperties>()

    private val pendingTaskUpdates = ArrayDeque<List<Task>>()

    private lateinit var timeToDueDateFormatter: TimeToDueDateFormatter

    private lateinit var taskColorizer: TaskColorizer

    private val scope = tasksViewModel.viewModelScope

    private val updateMutex = Mutex()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        timeToDueDateFormatter = TimeToDueDateFormatter(recyclerView.context)
        taskColorizer = TaskColorizer(recyclerView.context)
        viewUpdater = ViewUpdater(this, updateMutex)
    }

    fun setTasks(newTasks: List<Task>) {
        val copyOfNewTasks = newTasks.toList()
        pendingTaskUpdates.add(copyOfNewTasks)
        if (pendingTaskUpdates.size <= 1) {
            updateViewAndTasks(copyOfNewTasks)
        }
    }

    fun getTasks() = tasks.toList()

    private fun updateViewAndTasks(newTasks: List<Task>) {
        scope.launch(Dispatchers.Main) {
            val diffResult = withContext(Dispatchers.Default) {
                DiffUtil.calculateDiff(TasksDiffCallback(tasks, newTasks))
            }
            updateMutex.withLock {
                dispatchUpdatesToView(diffResult)
                updateTasks(newTasks)
            }
            updateView {}
            pendingTaskUpdates.remove()
            if (pendingTaskUpdates.size > 0) {
                updateViewAndTasks(pendingTaskUpdates.peek())
            }
        }
    }

    private fun dispatchUpdatesToView(diffResult: DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }

    private fun updateTasks(newTasks: List<Task>) {
        taskPropertyCache.keys.retainAll(newTasks)
        tasks.clear()
        tasks.addAll(newTasks)
    }

    fun updateView(completionCallback: () -> Unit) = viewUpdater.update(scope, completionCallback)

    fun updateTaskPropertyCache(newTaskPropertyCache: Map<Task, TaskViewProperties>) {
        taskPropertyCache.putAll(newTaskPropertyCache)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view, parent, false) as LinearLayout
        return TaskViewHolder(taskView, taskItemClickListener)
    }

    override fun getItemId(position: Int) = tasks[position].id

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        val cachedTaskProperties = getCachedTaskProperties(task)
        setTaskName(holder, task)
        setDueDate(holder, cachedTaskProperties)
        setFontColor(holder, cachedTaskProperties)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_DUE_DATE_VIEW)) {
            val cachedTaskProperties = getCachedTaskProperties(tasks[position])
            setDueDate(holder, cachedTaskProperties)
            setFontColor(holder, cachedTaskProperties)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun getCachedTaskProperties(task: Task) = taskPropertyCache.computeIfAbsent(task, this::createCachedTaskProperties)

    private fun createCachedTaskProperties(task: Task): TaskViewProperties {
        val isFulfilled = task.isFulfilled()
        val dueIn = task.dueIn()
        val timeToDueDate = formatDueDate(task.period, isFulfilled, dueIn)
        val fontColor = getTaskFontColor(isFulfilled, dueIn)
        return TaskViewProperties(timeToDueDate, fontColor)
    }

    fun formatDueDate(period: TimePeriod, taskIsFulfilled: Boolean, dueIn: Duration): String {
        return timeToDueDateFormatter.formatDueDate(period, taskIsFulfilled, dueIn)
    }

    fun getTaskFontColor(taskIsFulfilled: Boolean, dueIn: Duration): Int {
        return taskColorizer.getFontColor(taskIsFulfilled, dueIn)
    }

    private fun setTaskName(holder: TaskViewHolder, task: Task) {
        val taskView = holder.taskView
        val taskNameView = taskView.getChildAt(taskNameViewPosition) as TextView
        taskNameView.text = task.name
    }

    private fun setDueDate(holder: TaskViewHolder, taskViewProperties: TaskViewProperties) {
        val taskView = holder.taskView
        val dueDateView = taskView.getChildAt(dueDateViewPosition) as TextView
        dueDateView.text = taskViewProperties.timeToDueDate
    }

    private fun setFontColor(holder: TaskViewHolder, taskViewProperties: TaskViewProperties) {
        val taskView = holder.taskView
        val textView = taskView.getChildAt(taskNameViewPosition) as TextView
        val dueDateView = taskView.getChildAt(dueDateViewPosition) as TextView
        textView.setTextColor(taskViewProperties.fontColor)
        dueDateView.setTextColor(taskViewProperties.fontColor)
    }

    fun markTaskAsFulfilled(position: Int) {
        tasksViewModel.markTaskAsFulfilled(tasks[position])
    }

    class TaskViewHolder(
        val taskView: LinearLayout,
        private val taskItemClickListener: TaskItemClickListener
    ) : RecyclerView.ViewHolder(taskView), View.OnClickListener {

        init {
            taskView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            taskItemClickListener.taskItemClickedAt(this.layoutPosition)
        }

    }

    data class TaskViewProperties(val timeToDueDate: String, val fontColor: Int)

    enum class Payloads {
        UPDATE_DUE_DATE_VIEW
    }

}
