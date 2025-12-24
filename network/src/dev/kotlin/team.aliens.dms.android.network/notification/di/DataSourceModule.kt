package team.aliens.dms.android.network.notification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.network.notification.datasource.NetworkNotificationDataSource
import team.aliens.dms.android.network.notification.datasource.NetworkNotificationDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkNotificationDataSource(impl: NetworkNotificationDataSourceImpl): NetworkNotificationDataSource
}
