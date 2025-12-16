package team.aliens.dms.android.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.button.ButtonColor
import team.aliens.dms.android.core.designsystem.button.ButtonType
import team.aliens.dms.android.core.designsystem.button.DmsButton
import team.aliens.dms.android.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.textfield.DmsTextField
import team.aliens.dms.android.core.designsystem.titleB
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding

@Composable
internal fun SignIn(
//    navigateToMain: () -> Unit,
//    navigateToSignUp: () -> Unit,
//    navigateToFindId: () -> Unit,
//    navigateToFindPassword: () -> Unit,
   //onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    SignInScreen()
}

@Composable
private fun SignInScreen() {
    val id = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
            .background(DmsTheme.colorScheme.surfaceTint)
            .padding(top = 52.dp)
            .padding(horizontal = 24.dp)
            .pointerInput(Unit) { // TODO KMP 구현
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus(force = true)
                    }
                )
            },
    ) {
        DmsSymbol()
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "로그인",
            style = DmsTheme.typography.titleB,
            color = DmsTheme.colorScheme.onTertiaryContainer,
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
            modifier = Modifier
                .fillMaxWidth(),
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
            hint = "비빌번호 입력",
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

