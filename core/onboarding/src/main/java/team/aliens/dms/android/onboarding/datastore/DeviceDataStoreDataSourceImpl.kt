package team.aliens.dms.android.core.device.datastore

import team.aliens.dms.android.core.device.datastore.store.DeviceStore
import javax.inject.Inject

internal class DeviceDataStoreDataSourceImpl @Inject constructor(
    private val deviceStore: DeviceStore,
) : DeviceDataStoreDataSource() {
    override fun loadDeviceToken(): String = deviceStore.loadDeviceToken()

    override suspend fun storeDeviceToken(deviceToken: String) {
        deviceStore.storeDeviceToken(deviceToken)
    }

    override suspend fun clearDeviceToken() {
        deviceStore.clearDeviceToken()
    }
}
