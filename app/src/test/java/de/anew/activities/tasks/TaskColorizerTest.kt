package de.anew.activities.tasks

import android.graphics.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Duration

class TaskColorizerTest {

    @Test
    fun colorOfFulfilledTasks() {
        val backgroundColor = TaskColorizer.getBackgroundColor(true, Duration.ZERO)
        assertThat(backgroundColor).isEqualTo(Color.BLUE)
    }

    @Test
    fun colorOfDueTasks() {
        val backgroundColor = TaskColorizer.getBackgroundColor(false, Duration.ofHours(1))
        assertThat(backgroundColor).isEqualTo(Color.BLACK)
    }

    @Test
    fun colorOfOverdueTasks() {
        val backgroundColor = TaskColorizer.getBackgroundColor(false, Duration.ofHours(1).negated())
        assertThat(backgroundColor).isEqualTo(Color.DKGRAY)
    }

}