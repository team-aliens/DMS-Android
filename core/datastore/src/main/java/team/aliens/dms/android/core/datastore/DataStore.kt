package team.aliens.dms.android.core.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal val Context.dataStore: PreferencesDataStore by preferencesDataStore("dms-datastore")
