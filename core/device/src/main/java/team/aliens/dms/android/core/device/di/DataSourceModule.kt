package team.aliens.dms.android.core.device.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSource
import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindDeviceDataStoreDataSource(impl: DeviceDataStoreDataSourceImpl): DeviceDataStoreDataSource
}
