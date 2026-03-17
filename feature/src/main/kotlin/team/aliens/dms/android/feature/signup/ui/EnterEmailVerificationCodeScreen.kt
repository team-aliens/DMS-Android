package team.aliens.dms.android.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.VerificationCodeInput
import team.aliens.dms.android.core.designsystem.VerificationCodeInputDefaults
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.model.SignUpTextFieldError
import team.aliens.dms.android.feature.signup.model.isError
import team.aliens.dms.android.feature.signup.viewmodel.EnterEmailVerificationCodeSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.EnterEmailVerificationCodeState
import team.aliens.dms.android.feature.signup.viewmodel.EnterEmailVerificationCodeViewModel

private const val TIMER_TOTAL_SECONDS = 300

@Composable
internal fun EnterEmailVerificationCodeScreen(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToEnterStudentNumber: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterEmailVerificationCodeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    var remainingSeconds by remember { mutableIntStateOf(TIMER_TOTAL_SECONDS) }
    var timerRunning by remember { mutableStateOf(true) }

    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            remainingSeconds = TIMER_TOTAL_SECONDS
            while (remainingSeconds > 0) {
                delay(1000L)
                remainingSeconds--
            }
            viewModel.setTimerFinished(true)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber ->
                    navigateToEnterStudentNumber(effect.signUpData)
                is EnterEmailVerificationCodeSideEffect.ShowSendErrorSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "이메일 인증 코드 전송에 실패했어요.")
                is EnterEmailVerificationCodeSideEffect.ResetCountDownTimer -> {
                    timerRunning = false
                    viewModel.setTimerFinished(false)
                    timerRunning = true
                }
            }
        }
    }

    EnterEmailVerificationCodeContent(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        remainingSeconds = remainingSeconds,
        onEmailVerificationCodeChange = viewModel::setEmailVerificationCode,
        onResendEmailVerificationCode = viewModel::resendEmailVerificationCode,
    )
}

@Composable
private fun EnterEmailVerificationCodeContent(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterEmailVerificationCodeState,
    remainingSeconds: Int,
    onEmailVerificationCodeChange: (String) -> Unit,
    onResendEmailVerificationCode: () -> Unit,
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
                .horizontalPadding(24.dp)
                .topPadding(4.dp),
        )
        EmailVerificationInfoBanner(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(20.dp),
            email = state.email,
            remainingSeconds = remainingSeconds,
        )
        VerificationCodeInput(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            totalLength = 6,
            text = state.emailVerificationCode,
            onValueChange = onEmailVerificationCodeChange,
            supportingText = if (state.textFieldError.isError()) {
                {
                    VerificationCodeInputDefaults.SupportingText(
                        text = state.textFieldError.message ?: "",
                        isError = true,
                    )
                }
            } else null,
        )
        DmsButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .topPadding(20.dp),
            text = "인증코드 재발송",
            isLoading = state.isResendLoading,
            buttonType = ButtonType.Underline,
            buttonColor = ButtonColor.Gray,
            onClick = onResendEmailVerificationCode,
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

@Composable
private fun EmailVerificationInfoBanner(
    modifier: Modifier = Modifier,
    email: String,
    remainingSeconds: Int,
) {
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timerText = "%02d:%02d".format(minutes, seconds)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "이메일 인증",
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.onTertiaryContainer,
        )
        Column {
            Text(
                text = "$email 이메일로 전송된 ",
                style = DmsTheme.typography.bodyM,
                color = DmsTheme.colorScheme.inverseSurface,
            )
            Row {
                Text(
                    text = "인증번호 6자리를 ",
                    style = DmsTheme.typography.bodyM,
                    color = DmsTheme.colorScheme.inverseSurface,
                )
                Text(
                    text = timerText,
                    style = DmsTheme.typography.bodyM,
                    color = DmsTheme.colorScheme.error,
                )
                Text(
                    text = " 내로 입력해주세요.",
                    style = DmsTheme.typography.bodyM,
                    color = DmsTheme.colorScheme.inverseSurface,
                )
            }
        }
    }
}
