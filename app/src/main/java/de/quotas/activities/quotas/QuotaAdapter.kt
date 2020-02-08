package de.quotas.activities.quotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.quotas.R
import de.quotas.activities.quotas.QuotaAdapter.QuotaViewHolder
import de.quotas.models.Quota
class QuotaAdapter(
    private val quotasViewModel: QuotasViewModel,
    private val quotaItemClickListener: QuotaItemClickListener
) : RecyclerView.Adapter<QuotaViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotaViewHolder {
        val quotaView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quota_view, parent, false) as LinearLayout
        return QuotaViewHolder(quotaView, quotaItemClickListener)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        context = recyclerView.context
    }

    override fun getItemCount() = quotasViewModel.quotas.value?.size ?: 0

    override fun onBindViewHolder(holder: QuotaViewHolder, position: Int) {
        val quotaView = holder.quotaView
        val quotaNameView = quotaView.getChildAt(0) as TextView
        val dueDateView = quotaView.getChildAt(1) as TextView
        quotasViewModel.quotas.value?.let {
            val quota = it[position]
            quotaNameView.text = quota.name
            dueDateView.text = getFormattedDueDate(quota)
        }
    }

    private fun getFormattedDueDate(quota: Quota): String {
        val dueIn = quota.dueIn()
        val periodName = getLocalizedPeriodName(quota)
        val dueOrOverdueId = if (dueIn.isNegative) R.string.overdue_since else R.string.due_in
        val dueOrOverdue = context.getString(dueOrOverdueId)
        val formattedDuration = DurationFormatter(context).format(dueIn)
        return "%s · %s %s".format(periodName, dueOrOverdue, formattedDuration)
    }

    private fun getLocalizedPeriodName(quota: Quota): String {
        return when (quota.period::class.java.simpleName) {
            "Daily" -> context.getString(R.string.daily)
            "Weekly" -> context.getString(R.string.weekly)
            else -> "Undefined period"
        }
    }

    fun deleteQuotaAt(position: Int) {
        quotasViewModel.quotas.value?.get(position)?.let {
            quotasViewModel.deleteQuota(it)
        }
    }

    class QuotaViewHolder(
        val quotaView: LinearLayout,
        private val quotaItemClickListener: QuotaItemClickListener
    ) : RecyclerView.ViewHolder(quotaView), View.OnClickListener {

        init {
            quotaView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            quotaItemClickListener.quotaItemClickedAt(this.layoutPosition)
        }

    }

}

