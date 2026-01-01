package team.aliens.dms.android.feature.setting.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.setting.ui.Setting

@Composable
fun SettingRoute(
    onBackPressed: () -> Unit,
) {
    Setting(onBackPressed = onBackPressed)
}
