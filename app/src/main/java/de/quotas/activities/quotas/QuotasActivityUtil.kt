package de.quotas.activities.quotas

import android.content.Context
import de.quotas.models.QuotaRepository
import de.quotas.persistency.QuotasDatabase

object QuotasActivityUtil {

    fun getQuotasViewModelFacotry(context: Context): QuotasViewModelFactory {
        val quotaRepository = QuotaRepository(QuotasDatabase.getInstance(context).getQuotaDao())
        return QuotasViewModelFactory(quotaRepository)
    }

}