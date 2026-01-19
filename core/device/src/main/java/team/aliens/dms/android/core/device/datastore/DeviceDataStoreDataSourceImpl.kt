package team.aliens.dms.android.core.device.datastore

import team.aliens.dms.android.core.device.datastore.store.DeviceStore
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import javax.inject.Inject

internal class DeviceDataStoreDataSourceImpl @Inject constructor(
    private val deviceStore: DeviceStore,
) : DeviceDataStoreDataSource() {
    override suspend fun loadDeviceToken(): Result<String> = suspendRunCatching { deviceStore.loadDeviceToken() }

    override suspend fun storeDeviceToken(deviceToken: String): Result<Unit> = suspendRunCatching {
        deviceStore.storeDeviceToken(deviceToken)
    }

    override suspend fun clearDeviceToken(): Result<Unit> = suspendRunCatching {
        deviceStore.clearDeviceToken()
    }
}
