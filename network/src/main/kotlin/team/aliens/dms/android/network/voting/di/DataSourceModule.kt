package team.aliens.dms.android.network.voting.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.voting.datasource.NetworkVotingDataSource
import team.aliens.dms.android.network.voting.datasource.NetworkVotingDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindVotingNetworkDataSource(impl: NetworkVotingDataSourceImpl): NetworkVotingDataSource
}
