package de.quotas.activities.editor

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import de.quotas.QuotasApplication
import de.quotas.R
import de.quotas.activities.ActivityArgumentKeys.QUOTA_ID

class EditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_activity)
        setSupportActionBar(findViewById(R.id.editor_toolbar))
        val quotaId = intent.getLongExtra(QUOTA_ID, -1L)

        val quotasApplication = application as QuotasApplication
        val quotasRepository = quotasApplication.getModelComponent().getQuotasRepository()
        val editorViewModel = EditorViewModel(quotasRepository)
        val quota = editorViewModel.getQuota(quotaId)
        Log.d("qname", "Quota: $quota")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editor_menu, menu)
        return true
    }

}
