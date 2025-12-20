package team.aliens.dms.android.feature.main.mypage.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.feature.main.application.ui.Application

@Serializable
data object ApplicationRoute

@Composable
fun ApplicationRoute() {
    Application()
}
