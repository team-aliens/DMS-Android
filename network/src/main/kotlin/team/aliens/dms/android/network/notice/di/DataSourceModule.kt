package team.aliens.dms.android.network.notice.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.notice.datasource.NetworkNoticeDataSource
import team.aliens.dms.android.network.notice.datasource.NetworkNoticeDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkNoticeDataSource(impl: NetworkNoticeDataSourceImpl): NetworkNoticeDataSource
}
