package team.aliens.dms.android.feature.signin.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbolContent
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.notification.notificationPermissionGranted
import team.aliens.dms.android.feature.signin.viewmodel.SignInSideEffect
import team.aliens.dms.android.feature.signin.viewmodel.SignInState
import team.aliens.dms.android.feature.signin.viewmodel.SignInViewModel

@Composable
internal fun SignIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToFindPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SignInViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted)
            onShowSnackBar(DmsSnackBarType.ERROR, "알림을 받기 위해선 권한을 허용해주세요")
    }

    LaunchedEffect(Unit) {
        if (!notificationPermissionGranted(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SignInSideEffect.NavigateToMain -> navigateToMain()
                is SignInSideEffect.ShowSnackBar -> onShowSnackBar(it.snackBarType, it.message)
            }
        }
    }

    SignInScreen(
        onSignInClick = viewModel::signIn,
        navigateToSignUp = navigateToSignUp,
        state = state,
        onAccountIdChange = viewModel::setAccountId,
        onPasswordChange = viewModel::setPassword,
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun SignInScreen(
    onSignInClick: () -> Unit,
    navigateToSignUp: () -> Unit,
    state: SignInState,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClearFocus: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .systemBarsPadding()
            .navigationBarsPadding()
            .pointerInput(Unit) { // TODO KMP 구현
                detectTapGestures(
                    onTap = { onClearFocus() }
                )
            },
    ) {
        DmsSymbolContent(
            modifier = Modifier
                .topPadding(52.dp),
            title = "로그인"
        )
        UserInformationInputs(
            modifier = Modifier
                .topPadding(48.dp)
                .horizontalPadding(24.dp),
            accountId = state.accountId,
            onAccountIdChange = onAccountIdChange,
            password = state.password,
            onPasswordChange = onPasswordChange,
            onFindId = {},
            onResetPassword = {},
        )
        Spacer(modifier = Modifier.weight(1f))
        SignupActions(onSignUp = navigateToSignUp)
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "로그인",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onSignInClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier = Modifier,
    accountId: String,
    onAccountIdChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onFindId: () -> Unit,
    onResetPassword: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        DmsTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "아이디",
            value = accountId,
            hint = "아이디 입력",
            onValueChange = onAccountIdChange,
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            label = "비밀번호",
            value = password,
            hint = "비밀번호 입력",
            onValueChange = onPasswordChange,
            showVisibleIcon = true,
        )
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DmsButton(
                text = "아이디 찾기",
                buttonType = ButtonType.Text,
                buttonColor = ButtonColor.Gray,
                onClick = onFindId,
            )
            VerticalDivider(
                modifier = Modifier.height(12.dp),
                thickness = 1.dp,
                color = DmsTheme.colorScheme.inverseSurface,
            )
            DmsButton(
                text = "비밀번호 재설정",
                buttonType = ButtonType.Text,
                buttonColor = ButtonColor.Gray,
                onClick = onResetPassword,
            )
        }
    }
}

@Composable
private fun SignupActions(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "아직 회원이 아니신가요?",
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.inverseSurface,
        )
        DmsButton(
            text = "회원가입 하러가기",
            buttonType = ButtonType.Underline,
            buttonColor = ButtonColor.Gray,
            onClick = onSignUp,
        )
    }
}

