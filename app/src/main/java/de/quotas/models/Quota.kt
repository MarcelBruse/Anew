package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import de.quotas.models.time.TimePeriod
import de.quotas.persistency.PeriodConverter
import de.quotas.persistency.ZonedDateTimeConverter
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

@Entity
@TypeConverters(PeriodConverter::class, ZonedDateTimeConverter::class)
class Quota(
    @PrimaryKey(autoGenerate = true) val id: Long,
    name: String,
    period: TimePeriod,
    val startTime: ZonedDateTime,
    lastFulfillmentTime: ZonedDateTime?
) {

    var name: String = name
        set(name) {
            if (QuotaValidator.validateQuotaName(name).isEmpty()) {
                field = name
            }
        }

    var period: TimePeriod = period
        set(period) {
            if (QuotaValidator.validatePeriod(period).isEmpty()) {
                field = period
            }
        }

    var lastFulfillmentTime: ZonedDateTime? = lastFulfillmentTime
        set(lastFulfillmentTime) {
            if (QuotaValidator.validateLastFulfilmentDate(this, lastFulfillmentTime).isEmpty()) {
                field = lastFulfillmentTime
            }
        }

    fun isFulfilled(): Boolean {
        lastFulfillmentTime?.let {
            return period.currentIntervalIncludes(it)
        }
        return false
    }

    fun dueDate(): ZonedDateTime {
        lastFulfillmentTime?.let {
            return period.intervalIncluding(it).next().endsBefore()
        }
        return period.intervalIncluding(startTime).endsBefore()

    }

    fun dueIn(): Duration {
        val now = ZonedDateTime.now(period.clock())
        return Duration.between(now, dueDate())
    }

    fun isValid() = QuotaValidator.validate(this).isEmpty()

}