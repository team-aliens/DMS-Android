package team.aliens.dms.android.feature.home.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.feature.home.ui.Home

@Serializable
data object HomeRoute

@Composable
fun HomeRoute() {
    Home()
}
