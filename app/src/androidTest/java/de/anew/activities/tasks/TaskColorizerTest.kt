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