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

import androidx.recyclerview.widget.RecyclerView
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class TaskTouchHelperTest {

    private lateinit var taskAdapter: TaskAdapter

    @Before
    fun setUp() {
        taskAdapter = mockk()
        every { taskAdapter.markTaskAsFulfilled(any()) } returns Unit
    }

    @Test
    fun onMoveIsDisabled() {
        val taskTouchHelper = TaskTouchHelper(taskAdapter)
        val recyclerView = mockk<RecyclerView>()
        val viewHolder = mockk<RecyclerView.ViewHolder>()
        assertThat(taskTouchHelper.onMove(recyclerView, viewHolder, viewHolder)).isFalse()
    }

}