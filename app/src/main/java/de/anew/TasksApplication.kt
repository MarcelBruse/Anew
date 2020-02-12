package de.anew

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import de.anew.di.AppComponent
import de.anew.di.DaggerAppComponent
import de.anew.di.TasksRepositoryModule

class TasksApplication : Application() {

    private lateinit var modelComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(applicationContext)
        modelComponent = DaggerAppComponent
            .factory()
            .create(TasksRepositoryModule(applicationContext))
    }

    fun getModelComponent(): AppComponent {
        return modelComponent
    }

}