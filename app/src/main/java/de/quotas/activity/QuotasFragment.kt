package de.quotas.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.quotas.QuotasApplication
import de.quotas.R
import de.quotas.models.Quota

class QuotasFragment : Fragment() {

    companion object {
        fun newInstance() = QuotasFragment()
    }

    private lateinit var viewModel: QuotasViewModel

    private val dataSet = mutableListOf<String>()

    private val quotaAdapter = QuotaAdapter(dataSet)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentLayout = inflater.inflate(R.layout.quotas_fragment, container, false)
        val toolbar = fragmentLayout.findViewById(R.id.quotas_toolbar) as Toolbar
        (activity!! as AppCompatActivity).setSupportActionBar(toolbar)
        val quotasView = fragmentLayout.findViewById<View>(R.id.recycler_view) as RecyclerView
        quotasView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity!!)
            adapter = quotaAdapter
        }
        ItemTouchHelper(QuotaTouchHelper(quotaAdapter)).attachToRecyclerView(quotasView)
        return fragmentLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel(quotaAdapter)
    }

    private fun createViewModel(quotaAdapter: QuotaAdapter) {
        val quotasApplication = activity!!.application as QuotasApplication
        val quotaRepository = quotasApplication.getModelComponent().getQuotasRepository()
        viewModel = QuotasViewModel(quotaRepository)
        viewModel.quotas.observe(viewLifecycleOwner, createQuotaObserver(quotaAdapter))
    }

    private fun createQuotaObserver(quotaAdapter: QuotaAdapter): Observer<List<Quota>> {
        return Observer { quotas ->
            dataSet.clear()
            dataSet.addAll(quotas.map { quota -> quota.name }.toMutableList())
            quotaAdapter.notifyDataSetChanged()
        }
    }

}
