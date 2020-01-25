package de.quotas.models

import de.quotas.models.time.Daily
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object QuotaFactory {

    fun newQuota(): Quota {
        return Quota(0, "", Daily(), ZonedDateTime.now(ZoneId.systemDefault()), null)
    }

}