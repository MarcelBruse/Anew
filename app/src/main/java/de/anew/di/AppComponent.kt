package de.anew.di

import dagger.Component
import de.anew.models.task.TasksRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [TasksRepositoryModule::class])
abstract class AppComponent {

    abstract fun getTasksRepository(): TasksRepository

    @Component.Factory
    interface Factory {

        fun create(applicationContextModule: TasksRepositoryModule): AppComponent

    }

}