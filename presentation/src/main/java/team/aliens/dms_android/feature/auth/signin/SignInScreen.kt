package team.aliens.dms_android.feature.auth.signin

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
import androidx.compose.runtime.LaunchedEffect
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
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.button.DormTextCheckBox
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.common.initLocalAvailableFeatures
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.application.DmsAppState
import team.aliens.dms_android.feature.application.navigateToHome
import team.aliens.dms_android.feature.navigator.DmsRoute
import team.aliens.domain.exception.RemoteException
import team.aliens.presentation.R

@Composable
internal fun SignInScreen(
    appState: DmsAppState,
    signInViewModel: SignInViewModel = hiltViewModel(),
) {
    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()
    val signInButtonEnabled = uiState.signInButtonEnabled
    val signInSuccess = uiState.signInSuccess
    val error = uiState.error
    val navController = appState.navController
    val localAvailableFeatures = LocalAvailableFeatures.current

    val onAccountIdChange = { newAccountId: String ->
        signInViewModel.onEvent(SignInUiEvent.UpdateAccountId(newAccountId))
    }
    val onPasswordChange = { newPassword: String ->
        signInViewModel.onEvent(SignInUiEvent.UpdatePassword(newPassword))
    }
    val onAutoSignInOptionChanged = { newAutoSignInOption: Boolean ->
        signInViewModel.onEvent(SignInUiEvent.UpdateAutoSignInOption(newAutoSignInOption))
    }

    val onSignUpClicked = {
        navController.navigate(DmsRoute.SignUp.route)
    }
    val onFindIdClicked = {
        navController.navigate(DmsRoute.Auth.FindId)
    }
    val onResetPasswordClicked = {
        navController.navigate(DmsRoute.Auth.ResetPassword)
    }

    val onSignInButtonClicked = {
        signInViewModel.onEvent(SignInUiEvent.SignIn)
    }

    LaunchedEffect(uiState) {
        if (signInSuccess) {
            initLocalAvailableFeatures(
                container = localAvailableFeatures,
                mealService = uiState.features.mealService,
                noticeService = uiState.features.noticeService,
                pointService = uiState.features.pointService,
                studyRoomService = uiState.features.studyRoomService,
                remainsService = uiState.features.remainsService,
            )

            appState.navigateToHome()
        }
    }

    // fixme replace with new toast
    val toast = rememberToast()
    // todo discuss about this code
    val context = LocalContext.current
    LaunchedEffect(error) {
        // todo discuss 'bout this code
        if (error != null) {
            // todo toast
            toast(
                message = context.getString(
                    when (error) {
                        is RemoteException.BadRequest -> R.string.error_bad_request
                        is RemoteException.Unauthorized -> R.string.sign_in_error_unauthorized
                        is RemoteException.NotFound -> R.string.sign_in_error_not_found
                        is RemoteException.TooManyRequests -> R.string.error_too_many_request
                        is RemoteException.InternalServerError -> R.string.error_internal_server
                        else -> R.string.error_unknown
                    },
                ),
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(Modifier.height(92.dp))
        Banner()
        Spacer(Modifier.height(60.dp))
        UserInformationInputs(
            accountIdValue = uiState.accountId,
            passwordValue = uiState.password,
            autoSignInValue = uiState.autoSignIn,
            onAccountIdChange = onAccountIdChange, // 사용자의 행위에 대한 자동 콜백 = 능동형
            onPasswordChange = onPasswordChange,
            onAutoSignInOptionChanged = onAutoSignInOptionChanged, // 사용자의 행위 = 수동형(~ed)
            error = error,
        )
        Spacer(Modifier.height(12.dp))
        AuthActions(
            onSignUpClicked = onSignUpClicked,
            onFindIdClicked = onFindIdClicked,
            onResetPasswordClicked = onResetPasswordClicked,
        )
        Spacer(Modifier.weight(1f))
        DormContainedLargeButton(
            modifier = Modifier.padding(
                horizontal = 16.dp,
            ),
            text = stringResource(R.string.sign_in),
            color = DormButtonColor.Blue,
            enabled = signInButtonEnabled,
            onClick = onSignInButtonClicked,
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
    error: Throwable?,
) {
    val focusManager = LocalFocusManager.current
    val idErrorMessage = when (error) {
        is RemoteException.NotFound -> stringResource(R.string.sign_in_error_not_found)
        else -> null
    }

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
        error = idErrorMessage != null,
        errorDescription = idErrorMessage,
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
