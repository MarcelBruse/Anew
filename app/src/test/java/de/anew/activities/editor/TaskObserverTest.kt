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