package team.aliens.dms.android.core.theme.datastore.store

import kotlinx.coroutines.flow.Flow
import team.aliens.dms.android.core.theme.ThemeMode

internal abstract class ThemeStore {
    abstract suspend fun setThemeMode(mode: ThemeMode)
    abstract fun getThemeModeFlow(): Flow<ThemeMode>
}