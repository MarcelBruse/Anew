package de.quotas.activities.quotas

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.quotas.R

class QuotaAdapter(private val quotasViewModel: QuotasViewModel) : RecyclerView.Adapter<QuotaAdapter.QuotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotaViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quota_text_view, parent, false) as TextView
        return QuotaViewHolder(textView)
    }

    override fun getItemCount() = quotasViewModel.quotas.value?.size ?: 0

    override fun onBindViewHolder(holder: QuotaViewHolder, position: Int) {
        holder.textView.text = quotasViewModel.quotas.value?.get(position)?.name ?: ""
    }

    fun deleteQuotaAt(position: Int) {
        quotasViewModel.quotas.value?.get(position)?.let { quotasViewModel.deleteQuota(it) }
    }

    class QuotaViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}