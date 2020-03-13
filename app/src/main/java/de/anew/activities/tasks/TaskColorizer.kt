package de.anew.activities.tasks

import android.graphics.Color
import org.threeten.bp.Duration

object TaskColorizer {

    fun getBackgroundColor(isFulfilled: Boolean, dueIn: Duration): Int {
        return when {
            isFulfilled -> Color.BLUE
            dueIn.isNegative -> Color.DKGRAY
            else -> Color.BLACK
        }
    }

}