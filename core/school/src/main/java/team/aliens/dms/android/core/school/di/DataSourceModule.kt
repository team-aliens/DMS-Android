package team.aliens.dms.android.core.school.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.school.datastore.FeaturesDataStoreDataSource
import team.aliens.dms.android.core.school.datastore.FeaturesDataStoreDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindFeaturesDataSource(impl: FeaturesDataStoreDataSourceImpl): FeaturesDataStoreDataSource
}
