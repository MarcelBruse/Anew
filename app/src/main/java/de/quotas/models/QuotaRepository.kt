package de.quotas.models

import androidx.lifecycle.LiveData
import de.quotas.persistency.QuotaDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuotaRepository(private val quotaDao: QuotaDao) {

    fun getQuota(quotaId: Long): LiveData<Quota> {
        return quotaDao.load(quotaId)
    }

    fun getAllQuotas(): LiveData<List<Quota>> {
        return quotaDao.loadAll()
    }

    fun deleteQuota(quota: Quota) = GlobalScope.launch {
        quotaDao.delete(quota)
    }

}