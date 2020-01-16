package de.quotas.models

import androidx.lifecycle.MutableLiveData
import de.quotas.models.time.Daily
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object QuotaFactory {

    fun newQuota(): MutableLiveData<Quota> {
        val newQuota = Quota(0, "", Daily(), ZonedDateTime.now(ZoneId.systemDefault()), null)
        return MutableLiveData(newQuota)
    }

}