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
import team.aliens.dms.android.core.designsystem.VerificationCodeInput
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.viewmodel.EnterSchoolVerificationCodeSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.EnterSchoolVerificationCodeState
import team.aliens.dms.android.feature.signup.viewmodel.EnterSchoolVerificationCodeViewModel

internal const val SCHOOL_VERIFICATION_CODE_LENGTH = 8

@Composable
internal fun EnterSchoolVerificationCodeScreen(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterSchoolVerificationCodeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterSchoolVerificationCodeSideEffect.MoveToEnterSchoolVerificationQuestion ->
                    navigateToEnterSchoolVerificationQuestion(effect.signUpData)
                is EnterSchoolVerificationCodeSideEffect.ShowErrorSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "인증코드가 올바르지 않아요.")
            }
        }
    }

    EnterSchoolVerificationCodeContent(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onVerificationCodeChange = viewModel::setVerificationCode,
    )
}

@Composable
private fun EnterSchoolVerificationCodeContent(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterSchoolVerificationCodeState,
    onVerificationCodeChange: (String) -> Unit,
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
            title = "학교 인증코드 입력",
            description = "학교 인증코드는 ${SCHOOL_VERIFICATION_CODE_LENGTH}자리에요.",
        )
        VerificationCodeInput(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(48.dp),
            totalLength = SCHOOL_VERIFICATION_CODE_LENGTH,
            text = state.verificationCode,
            onValueChange = onVerificationCodeChange,
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
