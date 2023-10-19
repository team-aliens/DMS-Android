package team.aliens.dms.android.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

private const val TotalSecond = 180

@Destination
@Composable
internal fun SignUpEnterEmailVerificationCodeScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {/*

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    val localToast = LocalToast.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        signUpViewModel.postIntent(SignUpIntent.SendEmail.SetEmailTimerWorked(emailTimerWorked = true))
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.VerifyEmail.SuccessVerifyEmail -> {
                    navigator.openSetId()
                }

                is SignUpSideEffect.VerifyEmail.ConflictEmail -> {
                    localToast.showErrorToast(context.getString(R.string.sign_up_email_error_conflict))
                }

                else -> {}
            }
        }
    }

    val onVerificationCodeChange = { authCode: String ->
        if (authCode.length <= 6) {
            signUpViewModel.postIntent(
                SignUpIntent.VerifyEmail.SetAuthCode(
                    authCode = authCode,
                )
            )
            if (authCode.length == 6) {
                focusManager.clearFocus()
                signUpViewModel.postIntent(SignUpIntent.VerifyEmail.CheckEmailVerificationCode)
            }
        }
    }

    var isPressedBackButton by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        isPressedBackButton = true
    }

    if (isPressedBackButton) {
        DormCustomDialog(onDismissRequest = { *//*TODO*//* }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.CancelEmailVerify),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = {
                    navigator.openEnterEmail(clearStack = true)
                    signUpViewModel.postIntent(
                        SignUpIntent.VerifyEmail.SetAuthCode(
                            authCode = "",
                        )
                    )
                },
                onSubBtnClick = { isPressedBackButton = false },
            )
        }
    }

    LaunchedEffect(uiState.emailTimerWorked) {
        var min: String
        var sec: String
        repeat(TotalSecond) {
            min = ((TotalSecond - it) / 60).toString()
            sec = ((TotalSecond - it) % 60).toString().padStart(2, '0')
            signUpViewModel.postIntent(SignUpIntent.SendEmail.SetEmailExpirationTime("$min : $sec"))
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(108.dp))
            AppLogo(darkIcon = isSystemInDarkTheme())
            Space(space = 8.dp)
            Body2(text = stringResource(id = R.string.VerificationCode))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            BasicTextField(
                value = uiState.authCode,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                decorationBox = {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        items(6) { index ->
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = if (uiState.authCode.length - 1 >= index) DormTheme.colors.primaryVariant
                                        else DormTheme.colors.secondaryVariant,
                                    ),
                            )
                        }
                    }
                },
            )
            Space(space = 40.dp)
            Body3(
                text = stringResource(
                    id = if (uiState.authCodeMismatchError) R.string.sign_up_email_error_verification_code_incorrect
                    else R.string.sign_up_email_please_enter_verification_code,
                ),
                color = if (uiState.authCodeMismatchError) DormTheme.colors.error
                else DormTheme.colors.primaryVariant,
            )
            Space(space = 10.dp)
            Body3(
                text = uiState.emailExpirationTime,
                color = DormTheme.colors.primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonText(
                modifier = Modifier.dormClickable(
                    rippleEnabled = false,
                ) {
                    signUpViewModel.postIntent(
                        SignUpIntent.SendEmail.SetEmailTimerWorked(
                            emailTimerWorked = false
                        )
                    )
                    signUpViewModel.postIntent(SignUpIntent.SendEmail.SendEmailVerificationCode)
                },
                text = stringResource(id = R.string.ResendVerificationCode),
            )
            Space(space = 26.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = uiState.authCodeButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.VerifyEmail.CheckEmailVerificationCode)
            }
            Spacer(modifier = Modifier.height(82.dp))
        }
    }*/
}
