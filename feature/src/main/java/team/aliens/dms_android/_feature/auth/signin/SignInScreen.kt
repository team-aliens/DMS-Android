package team.aliens.dms_android._feature.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormTextCheckBox
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.LocalToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.dms_android.presentation.R

@Destination
@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToSignUpNav: () -> Unit,
    onNavigateToFindId: () -> Unit,
    onNavigateToResetPasswordNav: () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {
    val state by signInViewModel.stateFlow.collectAsStateWithLifecycle()
    val availableFeatures = LocalAvailableFeatures.current
    val toast = LocalToast.current
    val context = LocalContext.current // todo need to be discussed

    signInViewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            SignInSideEffect.IdNotFound -> toast.showErrorToast(
                message = context.getString(R.string.sign_in_error_id_not_found),
            )

            SignInSideEffect.BadRequest -> toast.showErrorToast(
                message = context.getString(R.string.sign_in_error_check_id_or_password),
            )

            SignInSideEffect.PasswordMismatch -> toast.showErrorToast(
                message = context.getString(R.string.sign_in_error_password_mismatch),
            )

            is SignInSideEffect.SignInSuccess -> {
                availableFeatures.features = sideEffect.features
                onNavigateToHome()
            }

            SignInSideEffect.DeviceTokenRegisteringFailure -> toast.showInformationToast(
                message = context.getString(R.string.sign_in_error_device_token_registering_failure),
            )
        }
    }
    val signInButtonEnabled = state.signInButtonEnabled

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Spacer(Modifier.height(92.dp))
        Banner()
        Spacer(Modifier.height(60.dp))
        UserInformationInputs(
            accountIdValue = state.accountId,
            passwordValue = state.password,
            autoSignInValue = state.autoSignIn,
            onAccountIdChange = { newAccountId: String ->
                signInViewModel.postIntent(SignInIntent.UpdateAccountId(newAccountId))
            },
            onPasswordChange = { newPassword: String ->
                signInViewModel.postIntent(SignInIntent.UpdatePassword(newPassword))
            },
            onAutoSignInOptionChanged = { newAutoSignInOption: Boolean ->
                signInViewModel.postIntent(SignInIntent.UpdateAutoSignInOption(newAutoSignInOption))
            },
            idError = state.idError,
            passwordError = state.passwordError,
        )
        Spacer(Modifier.height(12.dp))
        AuthActions(
            onSignUpClicked = onNavigateToSignUpNav,
            onFindIdClicked = onNavigateToFindId,
            onResetPasswordClicked = onNavigateToResetPasswordNav,
        )
        Spacer(Modifier.weight(1f))
        DormContainedLargeButton(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            ),
            text = stringResource(R.string.sign_in),
            color = DormButtonColor.Blue,
            enabled = signInButtonEnabled,
            onClick = { signInViewModel.postIntent(SignInIntent.SignIn) },
        )
        Spacer(Modifier.height(57.dp))
    }
}

@Composable
private fun Banner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        AppLogo()
        Spacer(Modifier.height(8.dp))
        Body2(
            text = stringResource(R.string.app_description),
        )
    }
}

@Composable
private fun UserInformationInputs(
    accountIdValue: String,
    passwordValue: String,
    autoSignInValue: Boolean,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAutoSignInOptionChanged: (Boolean) -> Unit,
    idError: Boolean,
    passwordError: Boolean,
) {
    val focusManager = LocalFocusManager.current

    // 아이디
    DormTextField(
        modifier = Modifier.padding(
            horizontal = 16.dp
        ),
        value = accountIdValue,
        onValueChange = onAccountIdChange,
        hint = stringResource(R.string.id),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            },
        ),
        error = idError,
        /*
                errorDescription = idErrorMessage,*/
    )
    Spacer(Modifier.height(32.dp))
    // 비밀번호
    DormTextField(
        modifier = Modifier.padding(
            horizontal = 16.dp
        ),
        value = passwordValue,
        onValueChange = onPasswordChange,
        isPassword = true,
        hint = stringResource(R.string.password),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
        ),
        error = passwordError,
        /*
                errorDescription = passwordErrorMessage,*/
    )
    Spacer(Modifier.height(28.dp))
    DormTextCheckBox(
        modifier = Modifier.padding(
            start = 12.dp,
        ),
        text = stringResource(R.string.sign_in_auto_sign_in),
        checked = autoSignInValue,
        onCheckedChange = onAutoSignInOptionChanged,
    )
    Spacer(Modifier.height(28.dp))
}

@Composable
private fun AuthActions(
    onSignUpClicked: () -> Unit,
    onFindIdClicked: () -> Unit,
    onResetPasswordClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(CenterHorizontally)
            .padding(
                horizontal = 10.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Caption(
            text = stringResource(R.string.do_sign_up),
            onClick = onSignUpClicked,
            color = DormTheme.colors.primaryVariant,
        )
        AuthActionDivider()
        Caption(
            text = stringResource(R.string.sign_in_find_id),
            onClick = onFindIdClicked,
            color = DormTheme.colors.primaryVariant,
        )
        AuthActionDivider()
        Caption(
            text = stringResource(R.string.change_password),
            onClick = onResetPasswordClicked,
            color = DormTheme.colors.primaryVariant,
        )
    }
}

@Composable
private fun AuthActionDivider() {
    Caption(
        text = "|",
        color = DormTheme.colors.primaryVariant,
    )
}
