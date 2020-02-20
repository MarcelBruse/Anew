package de.anew.models.task

import de.anew.models.task.Task
import de.anew.models.time.Daily
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object TaskFactory {

    fun newTask() = Task(
        0,
        "",
        Daily(),
        ZonedDateTime.now(ZoneId.systemDefault()),
        null
    )

}