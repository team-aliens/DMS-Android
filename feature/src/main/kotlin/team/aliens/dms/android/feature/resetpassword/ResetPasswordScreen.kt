package team.aliens.dms.android.feature.resetpassword

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.resetpassword.component.EmailVerificationContent
import team.aliens.dms.android.feature.resetpassword.component.InputIdContent
import team.aliens.dms.android.feature.resetpassword.component.InputNewPasswordContent
import team.aliens.dms.android.feature.resetpassword.component.InputUserInfoContent

private const val TIMER_TOTAL_SECONDS = 300

private val steps = ResetPasswordStep.entries

@Composable
internal fun ResetPasswordScreen(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: ResetPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val updatedOnNavigateBack by rememberUpdatedState(onNavigateBack)
    val updatedOnShowSnackBar by rememberUpdatedState(onShowSnackBar)

    var currentStepIndex by remember { mutableIntStateOf(0) }
    val currentStep = steps[currentStepIndex]
    val isLastStep = currentStepIndex == steps.lastIndex

    var remainingSeconds by remember { mutableIntStateOf(TIMER_TOTAL_SECONDS) }
    var timerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            remainingSeconds = TIMER_TOTAL_SECONDS
            while (remainingSeconds > 0) {
                delay(1000L)
                remainingSeconds--
            }
            viewModel.setEmailVerificationTimerFinished(true)
        }
    }

    BackHandler {
        if (currentStepIndex > 0) {
            currentStepIndex--
            viewModel.onStepChanged(steps[currentStepIndex])
        } else {
            updatedOnNavigateBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ResetPasswordSideEffect.NavigateUp -> updatedOnNavigateBack()
                is ResetPasswordSideEffect.ResetCountDownTimer -> {
                    timerRunning = false
                    viewModel.setEmailVerificationTimerFinished(false)
                    timerRunning = true
                }
                is ResetPasswordSideEffect.MoveToNext -> {
                    currentStepIndex++
                    viewModel.onStepChanged(steps[currentStepIndex])
                    if (steps[currentStepIndex] == ResetPasswordStep.InputEmailVerificationCode) {
                        timerRunning = true
                    }
                }
                is ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar -> updatedOnShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "존재하지 않는 계정 아이디입니다.",
                )
                is ResetPasswordSideEffect.ShowTooManyRequestSnackBar -> updatedOnShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.",
                )
                is ResetPasswordSideEffect.ShowServerErrorSnackBar -> updatedOnShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
                )
                is ResetPasswordSideEffect.ShowSendEmailSuccessSnackBar -> updatedOnShowSnackBar(
                    DmsSnackBarType.SUCCESS,
                    "이메일 인증 코드가 발송되었습니다.",
                )
                is ResetPasswordSideEffect.ShowPasswordResetSuccessSnackBar -> updatedOnShowSnackBar(
                    DmsSnackBarType.SUCCESS,
                    "비밀번호가 변경되었습니다.",
                )
            }
        }
    }

    ResetPasswordScreen(
        currentStep = currentStep,
        isLastStep = isLastStep,
        remainingSeconds = remainingSeconds,
        accountId = state.accountId,
        name = state.name,
        email = state.email,
        hashEmail = state.hashEmail,
        emailVerificationCode = state.emailVerificationCode,
        password = state.password,
        passwordConfirm = state.confirmPassword,
        buttonEnabled = state.buttonEnabled,
        isLoading = state.isLoading,
        isResendLoading = state.isResendLoading,
        emailVerificationCodeTextFieldError = state.emailVerificationCodeTextFieldError,
        passwordTextFieldError = state.passwordTextFieldError,
        confirmPasswordTextFieldError = state.confirmPasswordTextFieldError,
        onAccountIdChange = viewModel::setAccountId,
        onNameChange = { viewModel.setUserInfo(name = it) },
        onEmailChange = { viewModel.setUserInfo(email = it) },
        onEmailVerificationCodeChange = viewModel::setEmailVerificationCode,
        onResendEmailVerificationCode = viewModel::resendEmailVerificationCode,
        onPasswordChange = { viewModel.setPasswordInput(password = it) },
        onPasswordConfirmChange = { viewModel.setPasswordInput(confirmPassword = it) },
        onBackClick = {
            if (currentStepIndex > 0) {
                currentStepIndex--
                viewModel.onStepChanged(steps[currentStepIndex])
            } else {
                updatedOnNavigateBack()
            }
        },
        onContinueClick = { viewModel.moveNext(currentStep) },
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun ResetPasswordScreen(
    currentStep: ResetPasswordStep,
    isLastStep: Boolean,
    remainingSeconds: Int,
    accountId: String,
    name: String,
    email: String,
    hashEmail: String,
    emailVerificationCode: String,
    password: String,
    passwordConfirm: String,
    buttonEnabled: Boolean,
    isLoading: Boolean,
    isResendLoading: Boolean,
    emailVerificationCodeTextFieldError: team.aliens.dms.android.feature.resetpassword.model.ResetPasswordTextFieldError,
    passwordTextFieldError: team.aliens.dms.android.feature.resetpassword.model.ResetPasswordTextFieldError,
    confirmPasswordTextFieldError: team.aliens.dms.android.feature.resetpassword.model.ResetPasswordTextFieldError,
    onAccountIdChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onEmailVerificationCodeChange: (String) -> Unit,
    onResendEmailVerificationCode: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
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
        DmsTopAppBar(onBackClick = onBackClick)
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
            },
            label = "ResetPasswordStep",
        ) { step ->
            when (step) {
                ResetPasswordStep.InputId -> InputIdContent(
                    accountId = accountId,
                    onAccountIdChange = onAccountIdChange,
                    modifier = Modifier,
                )
                ResetPasswordStep.InputUserInfo -> InputUserInfoContent(
                    name = name,
                    email = email,
                    hashEmail = hashEmail,
                    onNameChange = onNameChange,
                    onEmailChange = onEmailChange,
                    modifier = Modifier,
                )
                ResetPasswordStep.InputEmailVerificationCode -> EmailVerificationContent(
                    email = email,
                    emailVerificationCode = emailVerificationCode,
                    remainingSeconds = remainingSeconds,
                    isResendLoading = isResendLoading,
                    textFieldError = emailVerificationCodeTextFieldError,
                    onEmailVerificationCodeChange = onEmailVerificationCodeChange,
                    onResendCode = onResendEmailVerificationCode,
                    modifier = Modifier,
                )
                ResetPasswordStep.InputNewPassword -> InputNewPasswordContent(
                    password = password,
                    passwordConfirm = passwordConfirm,
                    passwordTextFieldError = passwordTextFieldError,
                    passwordConfirmTextFieldError = confirmPasswordTextFieldError,
                    onPasswordChange = onPasswordChange,
                    onPasswordConfirmChange = onPasswordConfirmChange,
                    modifier = Modifier,
                )
            }
        }
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = if (isLastStep) "완료" else "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onContinueClick,
            enabled = buttonEnabled,
            isLoading = isLoading,
        )
    }
}
