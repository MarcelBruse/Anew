package de.quotas.models

import androidx.lifecycle.LiveData
import de.quotas.persistency.QuotaDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotaRepository @Inject constructor(
    private val quotaDao: QuotaDao
) {

    fun getQuota(quotaId: Long): LiveData<Quota> {
        return quotaDao.load(quotaId)
    }

}