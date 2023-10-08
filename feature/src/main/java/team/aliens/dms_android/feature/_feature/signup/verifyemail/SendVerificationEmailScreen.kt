package team.aliens.dms_android.feature._feature.signup.verifyemail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.LocalToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.feature.component.AppLogo
import team.aliens.dms_android.feature._feature.signup.SignUpIntent
import team.aliens.dms_android.feature._feature.signup.SignUpSideEffect
import team.aliens.dms_android.feature._feature.signup.SignUpViewModel
import team.aliens.dms_android.presentation.R

@Destination
@Composable
internal fun SendVerificationEmailScreen(
    onNavigateToVerifyEmail: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    var showQuitSignUpDialog by remember { mutableStateOf(false) }

    val toast = LocalToast.current
    val duplicateEmailErrorMessage = stringResource(id = R.string.sign_up_email_error_conflict)

    val onEmailChange = { email: String ->
        signUpViewModel.postIntent(
            SignUpIntent.SendEmail.SetEmail(
                email = email,
            )
        )
    }

    if (showQuitSignUpDialog) {
        DormCustomDialog(onDismissRequest = { /*TODO*/ }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.FinishSignUp),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = onNavigateToSignInWithInclusive,
                onSubBtnClick = { showQuitSignUpDialog = false },
            )
        }
    }


    BackHandler(enabled = true) {
        showQuitSignUpDialog = true
    }

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.SendEmail.AvailableEmail -> {
                    signUpViewModel.postIntent(SignUpIntent.SendEmail.SendEmailVerificationCode)
                }

                is SignUpSideEffect.SendEmail.DuplicatedEmail -> {
                    toast.showErrorToast(duplicateEmailErrorMessage)
                }

                is SignUpSideEffect.SendEmail.SuccessSendEmailVerificationCode -> {
                    onNavigateToVerifyEmail()
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
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(text = stringResource(id = R.string.sign_up_email_enter_email_address))
        Space(space = 86.dp)
        DormTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            hint = stringResource(id = R.string.sign_up_email_enter_email_address),
            error = uiState.emailFormatError,
            errorDescription = stringResource(id = R.string.sign_up_email_error_invalid_format),
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email,
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        DormContainedLargeButton(
            text = stringResource(id = R.string.sign_up_email_send_verification_code),
            color = DormButtonColor.Blue,
            enabled = uiState.sendEmailButtonEnabled,
        ) {
            signUpViewModel.postIntent(SignUpIntent.SendEmail.CheckEmailDuplication)
        }
        Spacer(modifier = Modifier.height(82.dp))
    }
}