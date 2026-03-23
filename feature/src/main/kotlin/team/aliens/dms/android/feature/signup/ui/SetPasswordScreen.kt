package team.aliens.dms.android.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.viewmodel.SetPasswordSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.SetPasswordState
import team.aliens.dms.android.feature.signup.viewmodel.SetPasswordViewModel

@Composable
internal fun SetPasswordScreen(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
) {
    val viewModel: SetPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val updatedNavigateToTerms by rememberUpdatedState(navigateToTerms)

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SetPasswordSideEffect.MoveToTerms -> updatedNavigateToTerms(effect.signUpData)
            }
        }
    }

    SetPasswordContent(
        onBack = onBack,
        onNextClick = viewModel::onNextClick,
        state = state,
        onPasswordChange = viewModel::setPassword,
        onPasswordCheckChange = viewModel::setPasswordCheck,
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun SetPasswordContent(
    onBack: () -> Unit,
    onNextClick: () -> Unit,
    state: SetPasswordState,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    onClearFocus: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClearFocus() })
            },
    ) {
        DmsTopAppBar(title = "회원가입", onBackClick = onBack, )
        DmsSymbolContent(
            modifier = Modifier
                .topPadding(4.dp),
            title = "비밀번호 입력",
            description = "영문, 숫자, 기호를 포함한 8~20자입니다.",
        )
        PasswordInputs(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            password = state.password,
            passwordCheck = state.passwordCheck,
            onPasswordChange = onPasswordChange,
            onPasswordCheckChange = onPasswordCheckChange,
            isPasswordFormatError = state.showPasswordDescription,
            isPasswordMatchError = state.showCheckPasswordDescription,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onNextClick,
            enabled = state.buttonEnabled,
        )
    }
}

@Composable
private fun PasswordInputs(
    password: String,
    passwordCheck: String,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    isPasswordFormatError: Boolean,
    isPasswordMatchError: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(44.dp),
    ) {
        DmsTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "비밀번호",
            hint = "비밀번호 입력",
            showVisibleIcon = true,
            isError = isPasswordFormatError,
            errorMessage = "형식이 일치하지 않습니다.",
        )
        DmsTextField(
            value = passwordCheck,
            onValueChange = onPasswordCheckChange,
            label = "비밀번호 확인",
            hint = "비밀번호 재입력",
            showVisibleIcon = true,
            isError = isPasswordMatchError,
            errorMessage = "비밀번호가 일치하지 않습니다.",
        )
    }
}
