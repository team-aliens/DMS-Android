package team.aliens.dms.android.core.theme.datastore.store

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.ThemeDataStore
import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.theme.ThemeMode
import javax.inject.Inject

internal class ThemeStoreImpl @Inject constructor(
    @ThemeDataStore private val themeDataStore: PreferencesDataStore,
) : ThemeStore() {

    override suspend fun setThemeMode(mode: ThemeMode) {
        transform {
            themeDataStore.edit { preferences ->
                preferences[THEME_MODE] = mode.name
            }
        }
    }

    override fun getThemeModeFlow(): Flow<ThemeMode> =
        themeDataStore.data.map { preferences ->
            runCatching {
                ThemeMode.valueOf(preferences[THEME_MODE] ?: ThemeMode.SYSTEM.name)
            }.getOrDefault(ThemeMode.SYSTEM)
        }

    private companion object {
        val THEME_MODE = stringPreferencesKey("theme-mode")
    }
}
