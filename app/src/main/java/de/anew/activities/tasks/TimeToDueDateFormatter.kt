/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import de.anew.models.time.TimePeriod
import de.anew.models.time.TimePeriodEnum
import org.threeten.bp.Duration

class TimeToDueDateFormatter(context: Context) {

    private val periodNames = hashMapOf(
        TimePeriodEnum.DAILY to context.getString(R.string.daily),
        TimePeriodEnum.WEEKLY to context.getString(R.string.weekly),
        TimePeriodEnum.UNDEFINED_PERIOD to "Unknown period"
    )
    
    private val doneLabel = context.getString(R.string.done)

    private val dueInLabel = context.getString(R.string.due_in)

    private val overdueSinceLabel = context.getString(R.string.overdue_since)

    private val durationFormatter = DurationFormatter(context)

    fun formatDueDate(period: TimePeriod, isFulfilled: Boolean, dueIn: Duration): String {
        val timePeriodEnum = TimePeriodEnum.getByTimePeriod(period)
        val periodName = periodNames[timePeriodEnum]
        val doneOrDueOrOverdue = getDoneOrDueOrOverdue(isFulfilled, dueIn)
        val formattedDuration = durationFormatter.format(dueIn)
        val periodAndStatus = "$periodName · $doneOrDueOrOverdue"
        return if (isFulfilled) periodAndStatus else "$periodAndStatus $formattedDuration"
    }

    private fun getDoneOrDueOrOverdue(isFulfilled: Boolean, dueIn: Duration): String {
        return when {
            isFulfilled -> doneLabel
            dueIn.isNegative -> overdueSinceLabel
            else -> dueInLabel
        }
    }

}