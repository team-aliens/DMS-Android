package team.aliens.dms.android.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@Destination
@Composable
internal fun EnterEmailScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {
/*
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
        DormCustomDialog(onDismissRequest = { *//*TODO*//* }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.FinishSignUp),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = navigator::openSignIn,
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
                    navigator.openSignUpEnterEmailVerificationCode()
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
    }*/
}