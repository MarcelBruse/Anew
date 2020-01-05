package de.quotas.activities.editor

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import de.quotas.QuotasApplication
import de.quotas.R
import de.quotas.activities.ActivityArgumentKeys.QUOTA_ID

class EditorActivity : AppCompatActivity() {

    private lateinit var editorViewModel: EditorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_activity)
        setSupportActionBar(findViewById(R.id.editor_toolbar))
        val quotaId = intent.getLongExtra(QUOTA_ID, -1L)
        editorViewModel = createEditorViewModel(quotaId)
        createView(editorViewModel)
    }

    private fun createEditorViewModel(quotaId: Long): EditorViewModel {
        val quotasApplication = application as QuotasApplication
        val quotasRepository = quotasApplication.getModelComponent().getQuotasRepository()
        val provider = ViewModelProviders.of(this, EditorViewModelFactory(quotasRepository, quotaId))
        return provider.get(EditorViewModel::class.java)
    }

    private fun createView(editorViewModel: EditorViewModel) {
        editorViewModel.quota.observe(this, Observer {
            val quotaNameField = findViewById<TextInputEditText>(R.id.name_field)
            quotaNameField.setText(it.name)
        })
        editorViewModel.quotaSavedEvent.observe(this, Observer { finish() })
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
        val quotaNameField = findViewById<TextInputEditText>(R.id.name_field)
        val quotaName = quotaNameField.text.toString()
        if (quotaName.isNotBlank()) {
            editorViewModel.saveQuota(quotaName)
        } else {
            val toast = Toast.makeText(this, "Quota name cannot be blank", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

}
