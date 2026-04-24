package team.aliens.dms.android.core.theme.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.theme.datastore.ThemeDataStoreDataSource
import team.aliens.dms.android.core.theme.datastore.ThemeDataStoreDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindThemeDataStoreDataSource(impl: ThemeDataStoreDataSourceImpl): ThemeDataStoreDataSource
}