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
package de.anew.activities.editor

import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import de.anew.models.task.TaskFactory
import de.anew.models.time.TimePeriodEnum
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class TaskObserverTest {

    @Test
    fun onChanged() {
        val textField = mockk<TextInputEditText>()
        every { textField.setText("Task") } returns Unit

        val periodSpinner = mockk<Spinner>()
        every { periodSpinner.setSelection(TimePeriodEnum.DAILY.ordinal) } returns Unit

        val task = TaskFactory.newTask()
        task.name = "Task"

        val taskObserver = TaskObserver(textField, periodSpinner)
        taskObserver.onChanged(task)

        verify { textField.setText("Task") }
        verify { periodSpinner.setSelection(0) }
    }

}