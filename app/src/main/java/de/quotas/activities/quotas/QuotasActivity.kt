package de.quotas.activities.quotas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.quotas.QuotasApplication
import de.quotas.R
import de.quotas.activities.editor.EditorActivity
import de.quotas.models.Quota

class QuotasActivity : AppCompatActivity() {

    private lateinit var viewModel: QuotasViewModel

    private val dataset = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotas)
        setSupportActionBar(findViewById(R.id.quotas_toolbar))

        val quotaAdapter = QuotaAdapter(dataset)
        createQuotasView(quotaAdapter)
        createViewModel(quotaAdapter)
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

    private fun createViewModel(quotaAdapter: QuotaAdapter) {
        val quotasApplication = application as QuotasApplication
        val quotaRepository = quotasApplication.getModelComponent().getQuotasRepository()
        viewModel = QuotasViewModel(quotaRepository)
        viewModel.quotas.observe(this, createQuotaObserver(quotaAdapter))
    }

    private fun createQuotaObserver(quotaAdapter: QuotaAdapter): Observer<List<Quota>> {
        return Observer { quotas ->
            dataset.clear()
            dataset.addAll(quotas.map { quota -> quota.name }.toMutableList())
            quotaAdapter.notifyDataSetChanged()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun openQuotaEditor(view: View) {
        val intent = Intent(this, EditorActivity::class.java)
        startActivity(intent)
    }

}
