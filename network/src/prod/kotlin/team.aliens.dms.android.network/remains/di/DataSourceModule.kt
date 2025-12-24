package team.aliens.dms.android.network.remains.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.remains.datasource.NetworkRemainsDataSource
import team.aliens.dms.android.network.remains.datasource.NetworkRemainsDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkRemainsDataSource(impl: NetworkRemainsDataSourceImpl): NetworkRemainsDataSource
}
