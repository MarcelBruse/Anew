package de.quotas.activities.quotas

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.quotas.R
import de.quotas.activities.quotas.QuotasActivityUtil.getQuotasViewModelFacotry
import de.quotas.models.Quota

class QuotasActivity : AppCompatActivity() {

    private val viewModel: QuotasViewModel by viewModels { getQuotasViewModelFacotry(applicationContext) }

    private val dataset = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val quotaAdapter = QuotaAdapter(dataset)
        createQuotasView(quotaAdapter)
        viewModel.quotas.observe(this, createQuotaObserver(quotaAdapter))
    }

    private fun createQuotasView(quotaAdapter: QuotaAdapter) {
        val linearLayoutManager = LinearLayoutManager(this)
        val quotasView = findViewById<View>(R.id.recycler_view) as RecyclerView
        quotasView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = quotaAdapter
        }
        ItemTouchHelper(QuotaTouchHelper(quotaAdapter)).attachToRecyclerView(quotasView)
    }

    private fun createQuotaObserver(quotaAdapter: QuotaAdapter): Observer<List<Quota>> {
        return Observer { quotas ->
            dataset.clear()
            dataset.addAll(quotas.map { quota -> quota.name }.toMutableList())
            quotaAdapter.notifyDataSetChanged()
        }
    }

}
