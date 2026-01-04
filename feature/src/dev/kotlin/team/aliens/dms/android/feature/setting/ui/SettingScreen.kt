package team.aliens.dms.android.feature.setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.button.DmsItemButton
import team.aliens.dms.android.core.designsystem.dialog.AlertDialog
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.titleB
import team.aliens.dms.android.core.designsystem.titleM
import team.aliens.dms.android.feature.setting.ui.component.SettingRotateContent
import team.aliens.dms.android.feature.setting.viewmodel.SettingSideEffect
import team.aliens.dms.android.feature.setting.viewmodel.SettingViewModel

@Composable
internal fun Setting(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    onNavigateSignIn: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SettingViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val (shouldShowSignOutDialog, onShouldShowSignOutDialogChange) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SettingSideEffect.CannotFetchNotificationStatus -> {
                    onShowSnackBar(DmsSnackBarType.ERROR, "알림 상태 조회를 실패했어요")
                }
                SettingSideEffect.SignOutSuccess -> onNavigateSignIn()
            }
        }
    }

    if (shouldShowSignOutDialog) {
        AlertDialog(
            title = { Text(text = "로그아웃", style = DmsTheme.typography.titleM) },
            text = { Text(text = "사용자 정보가 기기에서 지워집니다. 정말 로그아웃 하시겠습니까?", style = DmsTheme.typography.bodyM) },
            onDismissRequest = { onShouldShowSignOutDialogChange(false) },
            confirmButton = {
                DmsButton(
                    text = "확인",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = viewModel::signOut,
                )
            },
            dismissButton = {
                DmsButton(
                    text = "취소",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = { onShouldShowSignOutDialogChange(false) },
                )
            },
        )
    }

    SettingScreen(
        rotated = state.isOnNotification,
        onNavigateResetPassword = onNavigateResetPassword,
        onNotificationClick = { viewModel.updateNotificationStatus(state.isOnNotification) },
        onShowSignOutDialogChange = { onShouldShowSignOutDialogChange(true) },
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun SettingScreen(
    rotated: Boolean,
    onNavigateResetPassword: () -> Unit,
    onNotificationClick: () -> Unit,
    onShowSignOutDialogChange: () -> Unit,
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
                onClick = onShowSignOutDialogChange,
            )
        }
    }
}
