package team.aliens.dms.android.network.latestudy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.latestudy.datasource.NetworkLateStudyDataSource
import team.aliens.dms.android.network.latestudy.datasource.NetworkLateStudyDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class LateStudyDataSourceModule {

    @Binds
    abstract fun bindNetworkLateStudyDataSource(
        networkLateStudyDataSourceImpl: NetworkLateStudyDataSourceImpl,
    ): NetworkLateStudyDataSource
}
