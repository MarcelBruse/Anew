package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import org.threeten.bp.Duration

class TaskColorizer(context: Context) {

    private val lightFontColor = context.getColor(R.color.lightFont)

    private val darkFontColor = context.getColor(R.color.darkFont)

    private val fulfilledBackgroundColor = context.getColor(R.color.fulfilledGreen)

    private val overdueBackgroundColor = context.getColor(R.color.overdueYellow)

    private val noColor = 0x0

    fun getFontColor(isFulfilled: Boolean, dueIn: Duration): Int {
        return when {
            isFulfilled -> darkFontColor
            dueIn.isNegative -> darkFontColor
            else -> lightFontColor
        }
    }

    fun getBackgroundColor(isFulfilled: Boolean, dueIn: Duration): Int {
        return when {
            isFulfilled -> fulfilledBackgroundColor
            dueIn.isNegative -> overdueBackgroundColor
            else -> noColor
        }
    }

}