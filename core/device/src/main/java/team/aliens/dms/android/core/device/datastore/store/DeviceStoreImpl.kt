package team.aliens.dms.android.core.device.datastore.store

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.device.datastore.store.exception.CannotStoreDeviceTokenException
import team.aliens.dms.android.core.device.datastore.store.exception.DeviceTokenNotFoundException
import javax.inject.Inject

internal class DeviceStoreImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
) : DeviceStore() {
    override fun loadDeviceToken(): String = runBlocking {
        preferencesDataStore.data.map { preferences ->
            Log.d("TEST2",preferences[DEVICE_TOKEN].toString())
            preferences[DEVICE_TOKEN] ?: throw DeviceTokenNotFoundException()
        }.first()
    }

    override suspend fun storeDeviceToken(deviceToken: String) {
        transform(
            onFailure = { throw CannotStoreDeviceTokenException() },
        ) {
            Log.d("TEST1",deviceToken)
            preferencesDataStore.edit { preferences ->
                preferences[DEVICE_TOKEN] = deviceToken
            }
        }
    }

    override suspend fun clearDeviceToken() {
        transform {
            preferencesDataStore.edit { preferences -> preferences.clear() }
        }
    }

    private companion object {
        val DEVICE_TOKEN = stringPreferencesKey("device-token")
    }
}
