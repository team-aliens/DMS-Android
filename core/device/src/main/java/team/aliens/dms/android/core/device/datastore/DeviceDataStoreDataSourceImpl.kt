package team.aliens.dms.android.core.device.datastore

import team.aliens.dms.android.core.device.datastore.store.DeviceStore
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import javax.inject.Inject

internal class DeviceDataStoreDataSourceImpl @Inject constructor(
    private val deviceStore: DeviceStore,
) : DeviceDataStoreDataSource() {
    override fun loadDeviceToken() = suspendRunCatching { deviceStore.loadDeviceToken() }

    override suspend fun storeDeviceToken(deviceToken: String) = suspendRunCatching {
        deviceStore.storeDeviceToken(deviceToken)
    }

    override suspend fun clearDeviceToken() = suspendRunCatching {
        deviceStore.clearDeviceToken()
    }
}
