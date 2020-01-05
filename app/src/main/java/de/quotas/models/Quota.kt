package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import de.quotas.models.time.TimePeriod
import de.quotas.models.time.UndefinedPeriod
import de.quotas.persistency.PeriodConverter
import de.quotas.persistency.ZonedDateTimeConverter
import org.threeten.bp.ZonedDateTime

@Entity
@TypeConverters(PeriodConverter::class, ZonedDateTimeConverter::class)
class Quota(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var name: String?,
    var period: TimePeriod?,
    var startTime: ZonedDateTime?,
    var lastFulfillmentTime: ZonedDateTime?
) {

    fun isFulfilled(): Boolean {
        lastFulfillmentTime?.let {
            return period?.currentIntervalIncludes(it) == true
        }
        return false
    }

    fun isValid(): Boolean {
        val nameIsNotBlank = name?.isNotBlank() == true
        val hasDefinedPeriod = period?.equals(UndefinedPeriod) == false
        val hasLastFulfillmentTime = lastFulfillmentTime != null
        val startTimeSameOrBeforeFulfillmentTime = hasLastFulfillmentTime
                && startTime?.isAfter(lastFulfillmentTime) == false
        return nameIsNotBlank && hasDefinedPeriod && startTimeSameOrBeforeFulfillmentTime
    }

}