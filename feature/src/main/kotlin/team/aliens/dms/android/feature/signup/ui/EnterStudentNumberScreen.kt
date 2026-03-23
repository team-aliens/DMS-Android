package team.aliens.dms.android.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
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
import team.aliens.dms.android.feature.signup.viewmodel.EnterStudentNumberSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.EnterStudentNumberState
import team.aliens.dms.android.feature.signup.viewmodel.EnterStudentNumberViewModel

@Composable
internal fun EnterStudentNumberScreen(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterStudentNumberViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val updatedNavigateToSetId by rememberUpdatedState(navigateToSetId)
    val updatedOnShowSnackBar by rememberUpdatedState(onShowSnackBar)

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterStudentNumberSideEffect.MoveToSetId -> updatedNavigateToSetId(effect.signUpData)
                is EnterStudentNumberSideEffect.ShowConflictSnackBar ->
                    updatedOnShowSnackBar(DmsSnackBarType.ERROR, "이미 가입된 학번이에요")
                is EnterStudentNumberSideEffect.ShowErrorSnackBar ->
                    updatedOnShowSnackBar(DmsSnackBarType.ERROR, "학번을 확인해주세요")
            }
        }
    }

    EnterStudentNumberContent(
        onBack = onBack,
        onNextClick = viewModel::onNextClick,
        state = state,
        onGradeChange = viewModel::setGrade,
        onClassroomChange = viewModel::setClassRoom,
        onNumberChange = viewModel::setNumber,
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun EnterStudentNumberContent(
    onBack: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterStudentNumberState,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
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
            title = "학번 입력",
            description = "숫자만 입력해주세요.",
        )
        StudentNumberInputs(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            grade = state.grade,
            classroom = state.classroom,
            number = state.number,
            onGradeChange = onGradeChange,
            onClassroomChange = onClassroomChange,
            onNumberChange = onNumberChange,
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
private fun StudentNumberInputs(
    grade: String,
    classroom: String,
    number: String,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val classroomFocusRequest = remember { FocusRequester() }
    val numberFocusRequest = remember { FocusRequester() }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DmsTextField(
            modifier = Modifier.weight(1f),
            value = grade,
            onValueChange = { g ->
                onGradeChange(g)
                if (g.isNotEmpty()) classroomFocusRequest.requestFocus()
            },
            label = "학년",
            hint = "학년 입력",
            keyboardType = KeyboardType.Number,
        )
        DmsTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(classroomFocusRequest),
            value = classroom,
            onValueChange = { c ->
                onClassroomChange(c)
                if (c.isNotEmpty()) numberFocusRequest.requestFocus()
            },
            label = "반",
            hint = "반 입력",
            keyboardType = KeyboardType.Number,
        )
        DmsTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(numberFocusRequest),
            value = number,
            onValueChange = onNumberChange,
            label = "번호",
            hint = "번호 입력",
            keyboardType = KeyboardType.Number,
        )
    }
}
