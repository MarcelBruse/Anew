package de.quotas.activities.quotas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.quotas.QuotasApplication
import de.quotas.R
import de.quotas.activities.ActivityArgumentKeys.QUOTA_ID
import de.quotas.activities.editor.EditorActivity

class QuotasActivity : AppCompatActivity(), QuotaItemClickListener {

    private lateinit var quotasViewModel: QuotasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quotas_activity)
        setSupportActionBar(findViewById(R.id.quotas_toolbar))
        quotasViewModel = createQuotasViewModel()
        val quotaAdapter = QuotaAdapter(quotasViewModel, this)
        quotasViewModel.quotas.observe(this, Observer { quotaAdapter.notifyDataSetChanged() })
        val quotasView = createQuotasView(quotaAdapter)
        ItemTouchHelper(QuotaTouchHelper(quotaAdapter)).attachToRecyclerView(quotasView)
    }

    private fun createQuotasViewModel(): QuotasViewModel {
        val quotasApplication = application as QuotasApplication
        val quotasRepository = quotasApplication.getModelComponent().getQuotasRepository()
        val quotasViewModelFactory = QuotasViewModelFactory(quotasRepository)
        return ViewModelProviders.of(this, quotasViewModelFactory).get(QuotasViewModel::class.java)
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

    override fun quotaItemClickedAt(position: Int) {
        quotasViewModel.quotas.value?.elementAt(position)?.let {
            val intent = Intent(this, EditorActivity::class.java).apply {
                putExtra(QUOTA_ID, it.id)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.quotas_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.new_menu_item -> startActivity(Intent(this, EditorActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
