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
import androidx.test.platform.app.InstrumentationRegistry
import de.anew.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Duration

class TaskColorizerTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val taskColorizer: TaskColorizer = TaskColorizer(context)

    @Test
    fun colorOfFulfilledTasks() {
        val backgroundColor = taskColorizer.getFontColor(true, Duration.ZERO)
        assertThat(backgroundColor).isEqualTo(context.getColor(R.color.fulfilledGreen))
    }

    @Test
    fun colorOfDueTasks() {
        val backgroundColor = taskColorizer.getFontColor(false, Duration.ofHours(1))
        assertThat(backgroundColor).isEqualTo(context.getColor(R.color.lightFont))
    }

    @Test
    fun colorOfOverdueTasks() {
        val backgroundColor = taskColorizer.getFontColor(false, Duration.ofHours(1).negated())
        assertThat(backgroundColor).isEqualTo(context.getColor(R.color.overdueYellow))
    }

}