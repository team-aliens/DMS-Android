package team.aliens.dms.android.core.device.datastore

abstract class DeviceDataStoreDataSource {

    abstract suspend fun loadDeviceToken(): Result<String>

    abstract suspend fun storeDeviceToken(deviceToken: String): Result<Unit>

    abstract suspend fun clearDeviceToken(): Result<Unit>
}
