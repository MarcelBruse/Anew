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