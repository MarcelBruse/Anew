package de.anew.activities.editor

import android.widget.Spinner
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import de.anew.models.task.Task
import de.anew.models.time.TimePeriodEnum

class TaskObserver(
    private val taskNameField: TextInputEditText,
    private val timePeriodSpinner: Spinner
) : Observer<Task> {

    override fun onChanged(task: Task?) {
        task?.let {
            taskNameField.setText(it.name)
            val position = TimePeriodEnum.getByTimePeriod(it.period).ordinal
            timePeriodSpinner.setSelection(position)
        }
    }

}