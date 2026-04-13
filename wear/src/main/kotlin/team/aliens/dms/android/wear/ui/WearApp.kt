package team.aliens.dms.android.wear.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import team.aliens.dms.android.wear.data.WearStubRepository
import team.aliens.dms.android.wear.model.WearDestination
import team.aliens.dms.android.wear.presentation.home.WearHomeScreen
import team.aliens.dms.android.wear.presentation.meal.WearMealScreen
import team.aliens.dms.android.wear.presentation.notice.WearNoticeScreen
import team.aliens.dms.android.wear.presentation.status.WearStatusScreen

@Composable
fun WearApp() {
    val snapshot = remember { WearStubRepository.loadSnapshot() }
    var destination by rememberSaveable { mutableStateOf(WearDestination.HOME) }

    when (destination) {
        WearDestination.HOME -> WearHomeScreen(
            snapshot = snapshot,
            onNavigateMeal = { destination = WearDestination.MEAL },
            onNavigateStatus = { destination = WearDestination.STATUS },
            onNavigateNotice = { destination = WearDestination.NOTICE },
        )
        WearDestination.MEAL -> WearMealScreen(
            snapshot = snapshot,
            onBackClick = { destination = WearDestination.HOME },
        )
        WearDestination.STATUS -> WearStatusScreen(
            snapshot = snapshot,
            onBackClick = { destination = WearDestination.HOME },
        )
        WearDestination.NOTICE -> WearNoticeScreen(
            snapshot = snapshot,
            onBackClick = { destination = WearDestination.HOME },
        )
    }
}
