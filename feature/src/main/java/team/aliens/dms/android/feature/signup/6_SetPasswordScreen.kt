package team.aliens.dms.android.feature.signup

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
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.design_system.button.DormButtonColor
import team.aliens.dms.android.design_system.button.DormContainedLargeButton
import team.aliens.dms.android.design_system.extension.Space
import team.aliens.dms.android.design_system.modifier.dormClickable
import team.aliens.dms.android.design_system.textfield.DormTextField
import team.aliens.dms.android.design_system.theme.DormTheme
import team.aliens.dms.android.design_system.typography.Body2
import team.aliens.dms.android.design_system.typography.Caption
import team.aliens.dms.android.feature._legacy.AppLogo
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun SignUpSetPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    signUpViewModel: SignUpViewModel,
) {

    val uiState by signUpViewModel.stateFlow.collectAsState()

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
                    navigator.openSetProfileImage()
                }

                else -> {}
            }
        }
    }

    Column(
        modifier = modifier
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
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(id = R.string.EnterPassword),
                    isPassword = true,
                    error = uiState.passwordFormatError,
                    errorDescription = stringResource(id = R.string.CheckPasswordFormat),
                    imeAction = ImeAction.Next,
                )
            }
            DormTextField(
                value = uiState.passwordRepeat,
                onValueChange = onPasswordRepeatChange,
                hint = stringResource(id = R.string.ReEnterPassword),
                isPassword = true,
                error = uiState.passwordMismatchError,
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
            enabled = uiState.passwordButtonEnabled,
        ) {
            signUpViewModel.postIntent(SignUpIntent.SetPassword.CheckPassword)
        }
    }
}