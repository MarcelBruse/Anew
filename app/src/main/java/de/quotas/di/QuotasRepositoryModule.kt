package de.quotas.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.quotas.models.QuotaRepository
import de.quotas.persistency.QuotasDatabase
import javax.inject.Singleton

@Module
class QuotasRepositoryModule constructor(val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideQuotasRepository(): QuotaRepository {
        return QuotaRepository(QuotasDatabase.getInstance(applicationContext).getQuotaDao())
    }

}