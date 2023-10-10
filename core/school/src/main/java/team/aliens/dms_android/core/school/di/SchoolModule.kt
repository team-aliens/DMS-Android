package team.aliens.dms_android.core.school.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms_android.core.school.store.FeaturesStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SchoolModule {

    @Binds
    @Singleton
    abstract fun bindFeaturesStore(): FeaturesStoreImpl
}
