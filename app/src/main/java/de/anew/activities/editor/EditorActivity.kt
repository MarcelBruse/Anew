package de.anew.activities.editor

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import de.anew.R
import de.anew.TasksApplication
import de.anew.activities.ActivityArgumentKeys.TASK_ID
import de.anew.models.time.TimePeriod
import de.anew.models.time.TimePeriodEnum
import de.anew.models.time.TimePeriodFactory
import org.threeten.bp.Clock

class EditorActivity : AppCompatActivity() {

    private lateinit var editorViewModel: EditorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_activity)
        setSupportActionBar(findViewById(R.id.editor_toolbar))
        val taskId = intent.getLongExtra(TASK_ID, 0)
        editorViewModel = createEditorViewModel(taskId)
        initializePeriodSpinner()
        bindFieldsToData()
        setFocusToTaskNameField()
        editorViewModel.taskSavedOrDeletedEvent.observe(this, Observer { finish() })
    }

    private fun createEditorViewModel(taskId: Long): EditorViewModel {
        val tasksApplication = application as TasksApplication
        val tasksRepository = tasksApplication.getModelComponent().getTasksRepository()
        val provider = ViewModelProvider(this, EditorViewModelFactory(tasksRepository, taskId))
        return provider.get(EditorViewModel::class.java)
    }

    private fun initializePeriodSpinner() {
        val periodNames = arrayOf(getString(R.string.daily), getString(R.string.weekly))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, periodNames)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        val periodSpinner: Spinner = findViewById(R.id.period_spinner)
        periodSpinner.adapter = adapter
    }

    private fun bindFieldsToData() {
        val taskNameField: TextInputEditText = findViewById(R.id.name_field)
        val taskPeriodSpinner: Spinner = findViewById(R.id.period_spinner)
        val taskObserver = TaskObserver(taskNameField, taskPeriodSpinner)
        editorViewModel.task.observe(this, taskObserver)
    }

    private fun setFocusToTaskNameField() {
        val taskNameField: TextInputEditText = findViewById(R.id.name_field)
        if(taskNameField.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_menu_item -> {
                val saved = saveTaskIfPossible()
                item.isEnabled = !saved
            }
            R.id.delete_menu_item -> editorViewModel.deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTaskIfPossible(): Boolean {
        val taskName = getTaskNameFromView()
        val period = getPeriodFromView()
        val errorMessageIds = editorViewModel.validateUserInput(taskName, period)
        return if (errorMessageIds.isEmpty()) {
            editorViewModel.saveTask(taskName, period)
            true
        } else {
            errorMessageIds.forEach { displayErrorMessage(it) }
            false
        }
    }

    private fun getTaskNameFromView(): String {
        val taskNameField = findViewById<TextInputEditText>(R.id.name_field)
        return taskNameField.text.toString()
    }

    private fun getPeriodFromView(): TimePeriod {
        val periodSpinner: Spinner = findViewById(R.id.period_spinner)
        val periodEnum = TimePeriodEnum.values()[periodSpinner.selectedItemPosition]
        val periodFactory = TimePeriodFactory(Clock.systemDefaultZone())
        return periodFactory.timePeriodByEnum(periodEnum)
    }

    private fun displayErrorMessage(errorMessageId: Int) {
        val toast = Toast.makeText(this, getString(errorMessageId), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}
