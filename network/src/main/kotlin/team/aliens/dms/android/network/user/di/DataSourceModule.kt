package team.aliens.dms.android.network.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.user.datasource.NetworkUserDataSource
import team.aliens.dms.android.network.user.datasource.NetworkUserDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkUserDataSource(impl: NetworkUserDataSourceImpl): NetworkUserDataSource
}
