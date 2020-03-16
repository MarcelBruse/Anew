package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import org.threeten.bp.Duration

class TaskColorizer(context: Context) {

    private val defaultColor = context.getColor(R.color.lightFont)

    private val fulfilledGreen = context.getColor(R.color.fulfilledGreen)

    private val overdueYellow = context.getColor(R.color.overdueYellow)

    fun getFontColor(isFulfilled: Boolean, dueIn: Duration): Int {
        return when {
            isFulfilled -> fulfilledGreen
            dueIn.isNegative -> overdueYellow
            else -> defaultColor
        }
    }

}