package de.quotas.activities.quotas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.quotas.R

class QuotasActivity : AppCompatActivity() {

    private val dataset = mutableListOf("aaa", "bbb", "ccc", "ddd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        val quotaAdapter = QuotaAdapter(dataset)
        val quotasView = findViewById<View>(R.id.recycler_view) as RecyclerView
        quotasView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = quotaAdapter
        }

        ItemTouchHelper(QuotaTouchHelper(quotaAdapter)).attachToRecyclerView(quotasView)
    }

}
