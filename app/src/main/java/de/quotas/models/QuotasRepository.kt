package de.quotas.models

import de.quotas.persistency.QuotaDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuotasRepository(private val quotaDao: QuotaDao) {

    fun getQuota(quotaId: Long) = quotaDao.load(quotaId)

    fun getAllQuotas() = quotaDao.loadAll()

    fun getQuotaAsLiveData(quotaId: Long) = quotaDao.loadAsLiveData(quotaId)

    fun getAllQuotasAsLiveData() = quotaDao.loadAllAsLiveData()

    fun deleteQuota(quota: Quota) = GlobalScope.launch {
        quotaDao.delete(quota)
    }

}