package team.aliens.dms.android.network.outing.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.outing.datasource.OutingNetworkDataSource
import team.aliens.dms.android.network.outing.datasource.OutingNetworkDataSourceImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindOutingNetworkDataSource(impl: OutingNetworkDataSourceImpl): OutingNetworkDataSource
}
