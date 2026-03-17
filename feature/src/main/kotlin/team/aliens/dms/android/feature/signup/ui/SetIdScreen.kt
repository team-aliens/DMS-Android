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
import team.aliens.dms.android.feature.signup.viewmodel.SetIdSideEffect
import team.aliens.dms.android.feature.signup.viewmodel.SetIdState
import team.aliens.dms.android.feature.signup.viewmodel.SetIdViewModel

@Composable
internal fun SetIdScreen(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SetIdViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize(signUpData)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SetIdSideEffect.MoveToSetPassword -> navigateToSetPassword(effect.signUpData)
                is SetIdSideEffect.ShowConflictSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "이미 존재하는 아이디에요")
                is SetIdSideEffect.ShowErrorSnackBar ->
                    onShowSnackBar(DmsSnackBarType.ERROR, "아이디를 확인해주세요")
            }
        }
    }

    SetIdContent(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onIdChange = viewModel::setId,
    )
}

@Composable
private fun SetIdContent(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: SetIdState,
    onIdChange: (String) -> Unit,
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
            title = "아이디 입력",
            description = "DMS에서 사용될 아이디를 입력해주세요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            value = state.id,
            onValueChange = onIdChange,
            label = "아이디",
            hint = "아이디 입력",
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
