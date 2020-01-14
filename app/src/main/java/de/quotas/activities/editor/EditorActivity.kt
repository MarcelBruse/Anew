package de.quotas.activities.editor

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import de.quotas.QuotasApplication
import de.quotas.R
import de.quotas.activities.ActivityArgumentKeys.QUOTA_ID
import de.quotas.models.time.TimePeriod
import de.quotas.models.time.TimePeriodEnum
import de.quotas.models.time.TimePeriodFactory
import org.threeten.bp.Clock

class EditorActivity : AppCompatActivity() {

    private lateinit var editorViewModel: EditorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_activity)
        setSupportActionBar(findViewById(R.id.editor_toolbar))
        val quotaId = intent.getLongExtra(QUOTA_ID, -1L)
        editorViewModel = createEditorViewModel(quotaId)
        initializeQuotaNameField(editorViewModel)
        initializePeriodSpinner(editorViewModel)
        editorViewModel.quotaSavedEvent.observe(this, Observer { finish() })
    }

    private fun createEditorViewModel(quotaId: Long): EditorViewModel {
        val quotasApplication = application as QuotasApplication
        val quotasRepository = quotasApplication.getModelComponent().getQuotasRepository()
        val provider = ViewModelProviders.of(this, EditorViewModelFactory(quotasRepository, quotaId))
        return provider.get(EditorViewModel::class.java)
    }

    private fun initializeQuotaNameField(editorViewModel: EditorViewModel) {
        editorViewModel.quota.observe(this, Observer {
            val quotaNameField: TextInputEditText = findViewById(R.id.name_field)
            quotaNameField.setText(it.name)
        })
    }

    private fun initializePeriodSpinner(editorViewModel: EditorViewModel) {
        val periodNames = arrayOf(getString(R.string.daily), getString(R.string.weekly))
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, periodNames)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        val periodSpinner: Spinner = findViewById(R.id.period_spinner)
        periodSpinner.adapter = adapter
        editorViewModel.quota.observe(this, Observer {
            val position = TimePeriodEnum.getValueByTimePeriod(it.period).ordinal
            periodSpinner.setSelection(position)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_menu_item -> saveQuotaIfPossible()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun saveQuotaIfPossible() {
        val quotaName = getQuotaNameFromView()
        val period = getPeriodFromView()
        val errorMessageIds = editorViewModel.validateUserInput(quotaName, period)
        if (errorMessageIds.isEmpty()) {
            editorViewModel.saveQuota(quotaName, period)
        } else {
            errorMessageIds.forEach { displayErrorMessage(it) }
        }
    }

    private fun getQuotaNameFromView(): String {
        val quotaNameField = findViewById<TextInputEditText>(R.id.name_field)
        return quotaNameField.text.toString()
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
