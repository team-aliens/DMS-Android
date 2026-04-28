package team.aliens.dms.android.core.theme.datastore

import kotlinx.coroutines.flow.Flow
import team.aliens.dms.android.core.theme.ThemeMode
import team.aliens.dms.android.core.theme.datastore.store.ThemeStore
import javax.inject.Inject

internal class ThemeDataStoreDataSourceImpl @Inject constructor(
    private val themeStore: ThemeStore,
) : ThemeDataStoreDataSource() {

    override suspend fun setThemeMode(mode: ThemeMode) {
        themeStore.setThemeMode(mode)
    }

    override fun getThemeModeFlow(): Flow<ThemeMode> =
        themeStore.getThemeModeFlow()
}