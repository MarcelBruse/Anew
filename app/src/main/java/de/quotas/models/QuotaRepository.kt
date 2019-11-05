package de.quotas.models

import androidx.lifecycle.LiveData
import de.quotas.persistency.QuotaDao

class QuotaRepository constructor(
    private val quotaDao: QuotaDao
) {

    fun getQuota(quotaId: Long): LiveData<Quota> {
        return quotaDao.load(quotaId)
    }

    fun getAllQuotas(): LiveData<List<Quota>> {
        return quotaDao.loadAll()
    }

}