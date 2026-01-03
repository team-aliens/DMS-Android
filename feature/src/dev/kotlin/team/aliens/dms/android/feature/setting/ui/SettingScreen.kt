package team.aliens.dms.android.feature.setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.DmsItemButton
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.setting.ui.component.SettingRotateContent
import team.aliens.dms.android.feature.setting.viewmodel.SettingSideEffect
import team.aliens.dms.android.feature.setting.viewmodel.SettingViewModel

@Composable
internal fun Setting(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SettingViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SettingSideEffect.CannotFetchNotificationStatus -> {
                    onShowSnackBar(DmsSnackBarType.ERROR, "알림 상태 조회를 실패했어요")
                }
            }
        }
    }

    SettingScreen(
        rotated = state.isOnNotification,
        onNavigateResetPassword = onNavigateResetPassword,
        onNotificationClick = { viewModel.updateNotificationStatus(state.isOnNotification) },
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun SettingScreen(
    rotated: Boolean,
    onNavigateResetPassword: () -> Unit,
    onNotificationClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackPressed,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DmsTheme.colorScheme.background)
                .padding(horizontal = 10.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            DmsItemButton(
                iconRes = R.drawable.img_3d_key,
                text = "비밀번호 재설정",
                onClick = onNavigateResetPassword,
            )
            DmsItemButton(
                iconRes = R.drawable.img_calendar,
                text = "프로필 사진 변경",
                onClick = { },
            )
            SettingRotateContent(
                iconRes = R.drawable.img_repeat,
                text = "푸시 알림 켜기",
                rotated = rotated,
                onClick = onNotificationClick,
            )
            DmsItemButton(
                iconRes = R.drawable.img_3d_out,
                text = "로그아웃",
                onClick = { },
            )
        }
    }
}
