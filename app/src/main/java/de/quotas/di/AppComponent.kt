package de.quotas.di

import dagger.Component
import de.quotas.models.QuotaRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [QuotasRepositoryModule::class])
abstract class AppComponent {

    abstract fun getQuotasRepository(): QuotaRepository

    @Component.Factory
    interface Factory {

        fun create(applicationContextModule: QuotasRepositoryModule): AppComponent

    }

}