package team.aliens.dms_android.feature.signup.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpSideEffect
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.presentation.R

@Composable
internal fun SetPasswordScreen(
    onNavigateToSetProfile: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

    val state by signUpViewModel.stateFlow.collectAsState()

    val focusManager = LocalFocusManager.current

    val onPasswordChange = { password: String ->
        signUpViewModel.postIntent(SignUpIntent.SetPassword.SetPassword(password))
    }

    val onPasswordRepeatChange = { passwordRepeat: String ->
        signUpViewModel.postIntent(SignUpIntent.SetPassword.SetPasswordRepeat(passwordRepeat))
    }

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.SetPassword.SuccessCheckPassword -> {
                    onNavigateToSetProfile()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        AppLogo(darkIcon = isSystemInDarkTheme())
        Space(space = 8.dp)
        Body2(text = stringResource(id = R.string.SetPassword))
        Space(space = 4.dp)
        Caption(
            text = stringResource(id = R.string.PasswordWarning),
            color = DormTheme.colors.primaryVariant,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.824f)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(0.17f),
            ) {
                DormTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(id = R.string.EnterPassword),
                    isPassword = true,
                    error = state.passwordFormatError,
                    errorDescription = stringResource(id = R.string.CheckPasswordFormat),
                    imeAction = ImeAction.Next,
                )
            }
            DormTextField(
                value = state.passwordRepeat,
                onValueChange = onPasswordRepeatChange,
                hint = stringResource(id = R.string.ReEnterPassword),
                isPassword = true,
                error = state.passwordMismatchError,
                errorDescription = stringResource(id = R.string.CheckPassword),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                imeAction = ImeAction.Done,
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.Next),
            color = DormButtonColor.Blue,
            enabled = state.passwordButtonEnabled,
        ) {
            signUpViewModel.postIntent(SignUpIntent.SetPassword.CheckPassword)
        }
    }
}