package team.aliens.dms.android.feature.main.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.button.DmsItemButton
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.main.home.model.AnnouncementButton
import team.aliens.dms.android.feature.main.home.model.DmsPointContent
import team.aliens.dms.android.feature.main.home.model.HomeTopAppBar
import team.aliens.dms.android.feature.main.home.model.MealContent
import team.aliens.dms.android.feature.main.home.viewmodel.HomeSideEffect
import team.aliens.dms.android.feature.main.home.viewmodel.HomeState
import team.aliens.dms.android.feature.main.home.viewmodel.HomeViewModel

@Composable
internal fun Home(
    onNavigateNotice: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val gradient = Brush.radialGradient(
        colors = listOf(
            Color(0xFF002051).copy(alpha = 0.2f),
            Color(0xFF0F6EFE).copy(alpha = 0.2f),
        )
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowOutingPassDialog -> onShowSnackBar(
                    DmsSnackBarType.SUCCESS,
                    "개발중인 기능이에요",
                )
            }
        }
    }

    HomeScreen(
        state = state,
        gradient = gradient,
        onNavigateNotice = onNavigateNotice,
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateMeal = onNavigateMeal,
        onOutingPassClick = viewModel::showOutingPassDialog,
        onNotificationClick = onNavigateNotification,
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    gradient: Brush,
    onNavigateNotice: () -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onOutingPassClick: () -> Unit,
    onNotificationClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) {
        HomeTopAppBar(
            onOutingPassClick = onOutingPassClick,
            onNotificationClick = onNotificationClick,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp, vertical = 16.dp),
        ) {
            AnnouncementButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                onClick = onNavigateNotice,
            )
            MealContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                backgroundGradient = gradient,
                onMealClick = onNavigateMeal,
            )
            DmsPointContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                plusPoint = state.myPage.bonusPoint,
                minusPoint = state.myPage.minusPoint,
            )
            DmsItemButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                iconRes = R.drawable.img_calendar,
                text = "상벌점 이력 보러가기",
                onClick = { onNavigatePointHistory(PointType.ALL) },
            )
        }
    }
}
