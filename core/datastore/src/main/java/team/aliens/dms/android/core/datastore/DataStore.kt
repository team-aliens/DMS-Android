package team.aliens.dms.android.core.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal val Context.jwtStore: PreferencesDataStore by preferencesDataStore("jwt-datastore")

internal val Context.featuresStore: PreferencesDataStore by preferencesDataStore("features-datastore")

internal val Context.deviceStore: PreferencesDataStore by preferencesDataStore("device-datastore")
