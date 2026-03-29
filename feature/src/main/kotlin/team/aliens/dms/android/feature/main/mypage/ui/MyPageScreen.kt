package team.aliens.dms.android.feature.main.mypage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.DmsIconButton
import team.aliens.dms.android.core.designsystem.button.DmsItemButton
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.main.home.model.DmsPointContent
import team.aliens.dms.android.feature.main.mypage.ui.component.PhraseContent
import team.aliens.dms.android.feature.main.mypage.ui.component.ProfileContent
import team.aliens.dms.android.feature.main.mypage.viewmodel.MyPageSideEffect
import team.aliens.dms.android.feature.main.mypage.viewmodel.MyPageState
import team.aliens.dms.android.feature.main.mypage.viewmodel.MyPageViewModel

@Composable
internal fun MyPage(
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateNotification: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val updatedOnShowSnackBar by rememberUpdatedState(onShowSnackBar)

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is MyPageSideEffect.FailFetchMyPage -> updatedOnShowSnackBar(
                    DmsSnackBarType.ERROR, it.message,
                )
            }
        }
    }

    MyPageScreen(
        state = state,
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateNotification = onNavigateNotification,
        onSettingClick = onNavigateSetting,
    )
}

@Composable
private fun MyPageScreen(
    state: MyPageState,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateNotification: () -> Unit,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        DmsTopAppBar(
            actions = {
                DmsIconButton(
                    resource = DmsIcon.Setting,
                    tint = DmsTheme.colorScheme.scrim,
                    onClick = onSettingClick,
                )
            },
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 16.dp,
                    bottom = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            ProfileContent(
                gcn = state.myPage.gcn,
                name = state.myPage.name,
                schoolName = state.myPage.schoolName,
                genderType = state.myPage.sex,
                profileImageUrl = state.myPage.profileImageUrl,
            )
            PhraseContent(
                phrase = state.myPage.phrase ?: "",
            )
            DmsPointContent(
                modifier = Modifier,
                bonusPoint = state.myPage.bonusPoint,
                minusPoint = state.myPage.minusPoint,
            )
            DmsItemButton(
                iconRes = R.drawable.img_calendar,
                text = "상벌점 이력 보러가기",
                onClick = { onNavigatePointHistory(PointType.ALL) },
            )
            DmsItemButton(
                iconRes = R.drawable.img_3d_notification,
                text = "알림함",
                onClick = onNavigateNotification,
            )
        }
    }
}
