package de.quotas.models

import de.quotas.persistency.QuotaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuotasRepository(private val quotaDao: QuotaDao) {

    fun getQuota(quotaId: Long) = quotaDao.load(quotaId)

    fun getAllQuotas() = quotaDao.loadAll()

    suspend fun saveQuota(quota: Quota) = withContext(Dispatchers.IO) {
        launch { quotaDao.save(quota) }
    }

    suspend fun deleteQuota(quota: Quota) = withContext(Dispatchers.IO) {
        launch { quotaDao.delete(quota) }
    }

}