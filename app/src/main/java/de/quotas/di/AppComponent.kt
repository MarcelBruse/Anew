package de.quotas.di

import dagger.Component
import de.quotas.models.QuotasRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [QuotasRepositoryModule::class])
abstract class AppComponent {

    abstract fun getQuotasRepository(): QuotasRepository

    @Component.Factory
    interface Factory {

        fun create(applicationContextModule: QuotasRepositoryModule): AppComponent

    }

}