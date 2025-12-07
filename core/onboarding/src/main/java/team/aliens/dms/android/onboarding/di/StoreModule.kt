package team.aliens.dms.android.onboarding.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.onboarding.datastore.store.OnboardingStore
import team.aliens.dms.android.onboarding.datastore.store.OnboardingStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindOnboardingStore(impl: OnboardingStoreImpl): OnboardingStore
}
