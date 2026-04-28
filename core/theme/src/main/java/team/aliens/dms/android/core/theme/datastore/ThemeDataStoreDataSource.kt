package team.aliens.dms.android.core.theme.datastore

import kotlinx.coroutines.flow.Flow
import team.aliens.dms.android.core.theme.ThemeMode

abstract class ThemeDataStoreDataSource {
    abstract suspend fun setThemeMode(mode: ThemeMode)
    abstract fun getThemeModeFlow(): Flow<ThemeMode>
}