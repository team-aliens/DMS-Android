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
import team.aliens.dms.android.feature.signup.viewmodel.EnterSchoolVerificationQuestionSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.EnterSchoolVerificationQuestionState
import team.aliens.dms.android.feature.signup.viewmodel.EnterSchoolVerificationQuestionViewModel

@Composable
internal fun EnterSchoolVerificationQuestionScreen(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToEnterEmail: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterSchoolVerificationQuestionViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterSchoolVerificationQuestionSideEffect.MoveToEnterEmail ->
                    navigateToEnterEmail(effect.signUpData)
                is EnterSchoolVerificationQuestionSideEffect.ShowErrorSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "올바르지 않은 답변이에요")
                is EnterSchoolVerificationQuestionSideEffect.ShowQuestionErrorSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "질문을 불러오지 못했어요")
            }
        }
    }

    EnterSchoolVerificationQuestionContent(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onVerificationAnswerChange = viewModel::setSchoolVerificationAnswer,
    )
}

@Composable
private fun EnterSchoolVerificationQuestionContent(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterSchoolVerificationQuestionState,
    onVerificationAnswerChange: (String) -> Unit,
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
            title = state.schoolVerificationQuestion,
            description = "학교 확인 질문이에요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            value = state.schoolVerificationAnswer,
            onValueChange = onVerificationAnswerChange,
            label = "답변",
            hint = "답변 입력",
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
