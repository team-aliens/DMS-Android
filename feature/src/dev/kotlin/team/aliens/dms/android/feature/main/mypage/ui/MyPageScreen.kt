package team.aliens.dms.android.feature.main.mypage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import team.aliens.dms.android.feature.main.mypage.viewmodel.MyPageState
import team.aliens.dms.android.feature.main.mypage.viewmodel.MyPageViewModel

@Composable
internal fun MyPage(
    onNavigatePointHistory: (PointType) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        state = state,
        onNavigatePointHistory = onNavigatePointHistory,
        onSettingClick = { onShowSnackBar(DmsSnackBarType.SUCCESS, "준비중인 기능이에요") },
    )
}

@Composable
private fun MyPageScreen(
    state: MyPageState,
    onNavigatePointHistory: (PointType) -> Unit,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
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
                .fillMaxSize()
                .padding(
                    horizontal = 10.dp,
                    vertical = 16.dp,
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
                phrase = state.myPage.phrase,
            )
            DmsPointContent(
                modifier = Modifier,
                plusPoint = state.myPage.bonusPoint,
                minusPoint = state.myPage.minusPoint,
            )
            DmsItemButton(
                iconRes = painterResource(R.drawable.img_calendar),
                text = "상벌점 이력 보러가기",
                onClick = { onNavigatePointHistory(PointType.ALL) },
            )
        }
    }
}

