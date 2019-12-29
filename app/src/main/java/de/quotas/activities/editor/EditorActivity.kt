package de.quotas.activities.editor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
        return if (quotaId > -1) {
            EditorViewModel(quotasRepository, quotaId)
        } else {
            EditorViewModel(quotasRepository)
        }
    }

    private fun createView(editorViewModel: EditorViewModel) {
        val quotaNameField = findViewById<TextInputEditText>(R.id.name_field)
        quotaNameField.setText(editorViewModel.getQuotaName())
        editorViewModel.quotaSaved.observe(this, Observer { finish() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_menu_item -> {
                editorViewModel.saveQuota()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
