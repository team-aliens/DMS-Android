package team.aliens.dms.android.network.point.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.point.datasource.NetworkPointDataSource
import team.aliens.dms.android.network.point.datasource.NetworkPointDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkPointDataSource(impl: NetworkPointDataSourceImpl): NetworkPointDataSource
}
