package de.anew.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.anew.models.TasksRepository
import de.anew.persistency.TasksDatabase
import javax.inject.Singleton

@Module
class TasksRepositoryModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideTasksRepository(): TasksRepository {
        return TasksRepository(TasksDatabase.getInstance(applicationContext).getTaskDao())
    }

}