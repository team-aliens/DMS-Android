package team.aliens.dms.android.feature.resetpassword.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.resetpassword.viewmodel.ResetPasswordSideEffect
import team.aliens.dms.android.feature.resetpassword.viewmodel.ResetPasswordState
import team.aliens.dms.android.feature.resetpassword.viewmodel.ResetPasswordViewModel

@Composable
internal fun ResetPassword(
    onBackPressed: () -> Unit,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: ResetPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is ResetPasswordSideEffect.SuccessResetPassword -> onNavigateSetting()
                is ResetPasswordSideEffect.FailResetPassword -> onShowSnackBar(
                    DmsSnackBarType.ERROR, it.message
                )
            }
        }
    }

    ResetPasswordScreen(
        onBackPressed = onBackPressed,
        onResetPasswordClick = viewModel::resetPassword,
        state = state,
        onNewPasswordChange = viewModel::setNewPassword,
        onCheckNewPasswordChange = viewModel::setCheckNewPassword,
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun ResetPasswordScreen(
    onBackPressed: () -> Unit,
    onResetPasswordClick: () -> Unit,
    state: ResetPasswordState,
    onNewPasswordChange: (String) -> Unit,
    onCheckNewPasswordChange: (String) -> Unit,
    onClearFocus: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .pointerInput(Unit) { // TODO KMP 구현
                detectTapGestures(
                    onTap = { onClearFocus() }
                )
            },
    ) {
        DmsTopAppBar(onBackPressed = onBackPressed)
        DmsSymbolContent(
            modifier = Modifier
                .topPadding(52.dp),
            title = "비밀번호 재설정",
            description = "비밀번호를 다시 설정해주세요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .horizontalPadding(24.dp),
            label = "비밀번호",
            value = state.newPassword,
            hint = "비밀번호 입력",
            onValueChange = onNewPasswordChange,
            showVisibleIcon = true,
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .horizontalPadding(24.dp),
            label = "비밀번호 재입력",
            value = state.checkNewPassword,
            hint = "비밀번호 재입력",
            onValueChange = onCheckNewPasswordChange,
            showVisibleIcon = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "완료",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onResetPasswordClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}
