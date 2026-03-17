package team.aliens.dms.android.core.device.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.aliens.dms.android.core.device.datastore.store.DeviceStore
import team.aliens.dms.android.core.device.datastore.store.DeviceStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindDeviceStore(impl: DeviceStoreImpl): DeviceStore
}
