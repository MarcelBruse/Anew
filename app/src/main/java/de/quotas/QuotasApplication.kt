package de.quotas

import android.app.Application
import de.quotas.di.AppComponent
import de.quotas.di.DaggerAppComponent
import de.quotas.di.QuotasRepositoryModule

class QuotasApplication : Application() {

    private lateinit var modelComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        modelComponent = DaggerAppComponent
            .factory()
            .create(QuotasRepositoryModule(applicationContext))
    }

    fun getModelComponent(): AppComponent {
        return modelComponent
    }

}