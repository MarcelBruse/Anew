package de.anew.models

import de.anew.models.task.TaskFactory
import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TaskFactoryTest {

    @Test
    fun newTask() {
        val task = TaskFactory.newTask()
        assertThat(task.id).isEqualTo(0)
        assertThat(task.name).isEqualTo("")
        assertThat(task.period).isInstanceOf(Daily::class.java)
        assertThat(task.startTime).isInstanceOf(ZonedDateTime::class.java)
        assertThat(task.lastFulfillmentTime).isNull()
    }

}