package team.aliens.dms.android.core.device.datastore

import team.aliens.dms.android.core.device.datastore.store.DeviceStore
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import javax.inject.Inject

internal class DeviceDataStoreDataSourceImpl @Inject constructor(
    private val deviceStore: DeviceStore,
) : DeviceDataStoreDataSource() {
    override suspend fun loadDeviceToken(): Result<String> = runCatchingCancellable { deviceStore.loadDeviceToken() }

    override suspend fun storeDeviceToken(deviceToken: String): Result<Unit> = runCatchingCancellable {
        deviceStore.storeDeviceToken(deviceToken)
    }

    override suspend fun clearDeviceToken(): Result<Unit> = runCatchingCancellable {
        deviceStore.clearDeviceToken()
    }
}
