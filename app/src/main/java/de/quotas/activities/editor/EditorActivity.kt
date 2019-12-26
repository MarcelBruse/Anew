package de.quotas.activities.editor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import de.quotas.R
import de.quotas.activities.ActivityArgumentKeys.QUOTA_ID

class EditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_activity)
        val quotaId = intent.getLongExtra(QUOTA_ID, -1L)
        Log.d("QUOTA", "Quota Id $quotaId")
    }

}
