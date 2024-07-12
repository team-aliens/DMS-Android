package team.aliens.dms.android.core.device.datastore.store

internal abstract class DeviceStore {

    abstract fun loadDeviceToken(): String

    abstract suspend fun storeDeviceToken(deviceToken: String)

    abstract suspend fun clearDeviceToken()
}
