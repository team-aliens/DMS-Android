package team.aliens.dms.android.network.file.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.file.datasource.NetworkFileDataSource
import team.aliens.dms.android.network.file.datasource.NetworkFileDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkFileDataSource(impl: NetworkFileDataSourceImpl): NetworkFileDataSource
}
