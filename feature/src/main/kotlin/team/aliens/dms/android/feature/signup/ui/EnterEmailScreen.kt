package team.aliens.dms.android.feature.signup.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.viewmodel.EnterEmailSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.EnterEmailState
import team.aliens.dms.android.feature.signup.viewmodel.EnterEmailViewModel

@Composable
internal fun EnterEmailScreen(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToEnterEmailVerificationCode: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterEmailViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterEmailSideEffect.MoveToEnterEmailVerificationCode ->
                    navigateToEnterEmailVerificationCode(effect.signUpData)
                is EnterEmailSideEffect.ShowConflictSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "이미 가입된 이메일입니다")
                is EnterEmailSideEffect.ShowErrorSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "이메일을 확인해주세요")
            }
        }
    }

    EnterEmailContent(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onEmailChange = viewModel::setEmail,
    )
}

@Composable
private fun EnterEmailContent(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterEmailState,
    onEmailChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            title = "회원가입",
            onBackPressed = onBackPressed,
        )
        DmsSymbolContent(
            modifier = Modifier
                .topPadding(4.dp),
            title = "이메일을 입력",
            description = "인증 번호를 받을 이메일을 입력해주세요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            value = state.email,
            onValueChange = onEmailChange,
            label = "이메일",
            hint = "이메일 입력",
            showClearIcon = true,
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
            isLoading = state.isLoading,
        )
    }
}
