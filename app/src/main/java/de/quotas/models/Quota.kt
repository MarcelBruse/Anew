package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(PeriodConverter::class)
class Quota(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val period: Period,
    val lastFulfillmentTime: Long
) {

    fun isFulfilled(): Boolean {
        return true
    }

    enum class Period(val periodCode: Int) {
        UNDEFINED(0), DAILY(1), WEEKLY(2)
    }

}