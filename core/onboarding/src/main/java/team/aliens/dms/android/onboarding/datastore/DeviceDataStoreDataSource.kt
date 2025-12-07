package team.aliens.dms.android.core.device.datastore

abstract class DeviceDataStoreDataSource {

    abstract fun loadDeviceToken(): String

    abstract suspend fun storeDeviceToken(deviceToken: String)

    abstract suspend fun clearDeviceToken()
}
