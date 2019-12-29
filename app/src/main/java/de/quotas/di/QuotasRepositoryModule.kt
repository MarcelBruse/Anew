package de.quotas.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.quotas.models.QuotasRepository
import de.quotas.persistency.QuotasDatabase
import javax.inject.Singleton

@Module
class QuotasRepositoryModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideQuotasRepository(): QuotasRepository {
        return QuotasRepository(QuotasDatabase.getInstance(applicationContext).getQuotaDao())
    }

}