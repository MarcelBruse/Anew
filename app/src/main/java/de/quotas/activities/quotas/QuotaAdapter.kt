package de.quotas.activities.quotas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.quotas.R
import de.quotas.activities.quotas.QuotaAdapter.QuotaViewHolder

class QuotaAdapter(
    private val quotasViewModel: QuotasViewModel,
    private val quotaItemClickListener: QuotaItemClickListener
) : RecyclerView.Adapter<QuotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotaViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quota_text_view, parent, false) as TextView
        return QuotaViewHolder(textView, quotaItemClickListener)
    }

    override fun getItemCount() = quotasViewModel.quotas.value?.size ?: 0

    override fun onBindViewHolder(holder: QuotaViewHolder, position: Int) {
        holder.textView.text = quotasViewModel.quotas.value?.get(position)?.name ?: ""
    }

    fun deleteQuotaAt(position: Int) {
        quotasViewModel.quotas.value?.get(position)?.let {
            quotasViewModel.deleteQuota(it)
        }
    }

    class QuotaViewHolder(
        val textView: TextView,
        private val quotaItemClickListener: QuotaItemClickListener
    ) : RecyclerView.ViewHolder(textView), View.OnClickListener {

        init {
            textView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            quotaItemClickListener.quotaItemClickedAt(this.layoutPosition)
        }

    }

}