package team.aliens.dms.android.onboarding.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSource
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindOnboardingDataStoreDataSource(impl: OnboardingDataStoreDataSourceImpl): OnboardingDataStoreDataSource
}
