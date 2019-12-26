package de.quotas.activities.quotas

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.quotas.QuotasApplication
import de.quotas.R

class QuotasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quotas_activity)
        setSupportActionBar(findViewById(R.id.quotas_toolbar))

        val quotasViewModel = createQuotasViewModel()
        val quotaAdapter = QuotaAdapter(quotasViewModel)
        quotasViewModel.quotas.observe(this, Observer { quotaAdapter.notifyDataSetChanged() })
        val quotasView = createQuotasView(quotaAdapter)
        ItemTouchHelper(QuotaTouchHelper(quotaAdapter)).attachToRecyclerView(quotasView)
    }

    private fun createQuotasViewModel(): QuotasViewModel {
        val quotasApplication = application as QuotasApplication
        val quotaRepository = quotasApplication.getModelComponent().getQuotasRepository()
        return QuotasViewModel(quotaRepository)
    }

    private fun createQuotasView(quotaAdapter: QuotaAdapter): RecyclerView {
        val linearLayoutManager = LinearLayoutManager(this)
        val quotasView = findViewById<View>(R.id.recycler_view) as RecyclerView
        quotasView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = quotaAdapter
        }
        return quotasView
    }

}
