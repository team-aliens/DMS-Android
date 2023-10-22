package team.aliens.dms.android.core.school.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.school.datastore.store.FeaturesStore
import team.aliens.dms.android.core.school.datastore.store.FeaturesStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindFeaturesStore(impl: FeaturesStoreImpl): FeaturesStore
}
