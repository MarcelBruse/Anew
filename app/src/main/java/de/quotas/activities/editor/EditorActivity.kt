package de.quotas.activities.editor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.quotas.R

class EditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setSupportActionBar(findViewById(R.id.editor_toolbar))
    }
}
