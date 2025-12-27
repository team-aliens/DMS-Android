package team.aliens.dms.android.feature.remain.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.remain.ui.RemainApplication

@Composable
fun RemainApplicationRoute(
    onNavigateBack: (String) -> Unit,
) {
    RemainApplication(
        onNavigateBack = onNavigateBack,
    )
}
