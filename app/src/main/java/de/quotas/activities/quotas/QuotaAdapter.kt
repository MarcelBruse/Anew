package de.quotas.activities.quotas

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.quotas.R

class QuotaAdapter(private val dataset: MutableList<String>) : RecyclerView.Adapter<QuotaAdapter.QuotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotaViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quota_text_view, parent, false) as TextView
        return QuotaViewHolder(textView)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: QuotaViewHolder, position: Int) {
        holder.textView.text = dataset[position]
    }

    fun removeDataAt(position: Int) {
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }

    class QuotaViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}