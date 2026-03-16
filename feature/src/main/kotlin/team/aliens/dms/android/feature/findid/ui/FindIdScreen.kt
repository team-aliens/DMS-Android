package team.aliens.dms.android.feature.findid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.dialog.AlertDialog
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.sTitleM
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.feature.findid.component.UserInfoInputContent

@Composable
internal fun FindIdScreen(
    onNavigateToBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: FindIdViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is FindIdSideEffect.NavigateBack -> onNavigateToBack()
                is FindIdSideEffect.ShowServerErrorSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "서버에 문제가 발생했습니다.",
                )
                is FindIdSideEffect.ShowNumberErrorSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "학번을 확인해주세요.",
                )
            }
        }
    }

    if (state.isShowIdDialog) {
        AlertDialog(
            title = { Text(text = "아이디 찾기", style = DmsTheme.typography.sTitleM) },
            text = { Text(text = "회원님의 아이디는 ${state.hashedEmail}입니다.", style = DmsTheme.typography.bodyM) },
            onDismissRequest = viewModel::hideDialog,
            confirmButton = {
                DmsButton(
                    text = "확인",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = viewModel::hideDialog,
                )
            },
        )
    }

    FindIdScreen(
        name = state.name,
        grade = state.grade,
        classroom = state.classRoom,
        number = state.number,
        buttonEnabled = state.buttonEnabled,
        isLoading = state.isLoading,
        onNameChange = viewModel::setName,
        onGradeChange = viewModel::setGrade,
        onClassroomChange = viewModel::setClassRoom,
        onNumberChange = viewModel::setNumber,
        onFindIdClick = viewModel::findId,
        onBackClick = viewModel::navigateBack,
    )
}

@Composable
private fun FindIdScreen(
    name: String,
    grade: String,
    classroom: String,
    number: String,
    buttonEnabled: Boolean,
    isLoading: Boolean,
    onNameChange: (String) -> Unit,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onFindIdClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DmsTopAppBar(onBackPressed = onBackClick)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(DmsTheme.colorScheme.surfaceTint)
                .padding(paddingValues),
        ) {
            DmsSymbolContent(
                modifier = Modifier
                    .topPadding(4.dp),
                title = "아이디 찾기",
            )
            UserInfoInputContent(
                modifier = Modifier
                    .topPadding(48.dp)
                    .horizontalPadding(24.dp),
                name = name,
                grade = grade,
                classroom = classroom,
                number = number,
                onNameChange = onNameChange,
                onGradeChange = onGradeChange,
                onClassroomChange = onClassroomChange,
                onNumberChange = onNumberChange,
            )
            Spacer(modifier = Modifier.weight(1f))
            DmsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "아이디 찾기",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                keyboardInteractionEnabled = true,
                onClick = onFindIdClick,
                enabled = buttonEnabled,
                isLoading = isLoading,
            )
        }
    }
}
