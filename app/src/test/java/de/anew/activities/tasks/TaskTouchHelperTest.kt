package de.anew.activities.tasks

import androidx.recyclerview.widget.RecyclerView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class TaskTouchHelperTest {

    private lateinit var taskAdapter: TaskAdapter

    @Before
    fun setUp() {
        taskAdapter = mockk()
        every { taskAdapter.deleteTaskAt(any()) } returns Unit
    }

    @Test
    fun onMoveIsDisabled() {
        val taskTouchHelper = TaskTouchHelper(taskAdapter)
        val recyclerView = mockk<RecyclerView>()
        val viewHolder = mockk<RecyclerView.ViewHolder>()
        assertThat(taskTouchHelper.onMove(recyclerView, viewHolder, viewHolder)).isFalse()
    }

    @Test
    fun deletesTaskOnSwiped() {
        val taskTouchHelper = TaskTouchHelper(taskAdapter)
        val viewHolder = mockk<RecyclerView.ViewHolder>()
        val adapterPosition = 4
        every { viewHolder.adapterPosition } returns adapterPosition
        taskTouchHelper.onSwiped(viewHolder, adapterPosition)
        verify { taskAdapter.deleteTaskAt(adapterPosition) }
    }

}