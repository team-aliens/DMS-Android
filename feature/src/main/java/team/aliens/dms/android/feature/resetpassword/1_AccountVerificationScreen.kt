package team.aliens.dms.android.feature.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavigator

// TODO Pop backstack
@Destination
@Composable
fun AccountVerificationScreen(
    modifier: Modifier = Modifier,
    navigator: ResetPasswordNavigator,
    // changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(), // fixme
    // resetPasswordVerificationViewModel: ResetPasswordVerificationViewModel = hiltViewModel(), // fixme
) {/*

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val toast = rememberToast()

    val pattern = Patterns.EMAIL_ADDRESS

    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    var isIdError by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

    val onIdChange = { userId: String ->
        if (userId.length != id.length) isIdError = false
        id = userId
    }

    val onNameChange = { userName: String ->
        if (userName.length != name.length) isNameError = false
        name = userName
    }

    val onEmailChange = { value: String ->
        if (value.length != userEmail.length) isEmailError = false
        userEmail = value
    }

    var emailResponse by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        changePasswordViewModel.editPasswordEffect.collect {
            when (it) {
                is ChangePasswordViewModel.Event.CheckIdSuccess -> {
                    emailResponse = it.email
                }

                is ChangePasswordViewModel.Event.NotFoundException -> {
                    isIdError = true
                }

                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        resetPasswordVerificationViewModel.registerEmailEvent.collect {
            when (it) {
                is ResetPasswordVerificationEvent.SendEmailSuccess -> navigator::openResetPasswordEnterEmailVerificationCode
                is ResetPasswordVerificationEvent.TooManyRequestsException -> {
                    toast(context.getString(R.string.Retry))
                }

                else -> toast(
                    getStringFromEmailEvent(
                        context = context,
                        event = it,
                    )
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .fillMaxHeight(0.8f)
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            }
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(
            text = stringResource(id = R.string.Identification),
        )
        Column(
            modifier = Modifier
                .padding(top = 60.dp)
        ) {
            Box(
                modifier = Modifier.height(68.dp),
            ) {
                DormTextField(
                    value = id,
                    onValueChange = onIdChange,
                    hint = stringResource(id = R.string.EnterId),
                    error = isIdError,
                    errorDescription = stringResource(id = R.string.NotExistId),
                    keyboardActions = KeyboardActions {
                        if (id.isNotBlank()) {
                            changePasswordViewModel.checkId(
                                accountId = id,
                            )
                            focusManager.clearFocus()
                        }
                    },
                    imeAction = ImeAction.Done,
                )
            }
            AnimatedVisibility(
                visible = emailResponse.isNotBlank()
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                DormTheme.colors.background,
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp,
                            )
                    ) {
                        Body2(
                            text = stringResource(id = R.string.MatchEmailToId),
                        )
                        Body2(
                            modifier = Modifier.padding(
                                top = 8.dp,
                            ),
                            text = emailResponse,
                            color = DormTheme.colors.primary,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(68.dp)
                            .padding(top = 24.dp)
                    ) {
                        DormTextField(
                            value = name,
                            onValueChange = onNameChange,
                            hint = stringResource(id = R.string.EnterName),
                            imeAction = ImeAction.Next,
                        )
                    }
                    Space(space = 32.dp)
                    Box(
                        modifier = Modifier
                            .height(68.dp)
                    ) {
                        DormTextField(
                            value = userEmail,
                            onValueChange = onEmailChange,
                            hint = stringResource(id = R.string.EnterEmailAddress),
                            error = isEmailError,
                            keyboardType = KeyboardType.Email,
                            errorDescription = context.getString(R.string.NotValidEmailFormat),
                            keyboardActions = KeyboardActions {
                                focusManager.clearFocus()
                            },
                            imeAction = ImeAction.Done,
                        )
                    }
                }
            }

            RatioSpace(height = if (emailResponse.isEmpty()) 0.05f else 0.622f)

            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = if (emailResponse.isEmpty()) id.isNotBlank() && !isIdError
                else (id.isNotBlank() && name.isNotBlank() && userEmail.isNotBlank())
            ) {
                if (id.isNotBlank() && name.isNotBlank() && userEmail.isNotBlank()) {
                    if (pattern.matcher(userEmail).find()) {
                        resetPasswordVerificationViewModel.requestEmailCode(
                            email = userEmail.trim(),
                            type = EmailVerificationType.PASSWORD,
                        )
                    } else {
                        isEmailError = true
                    }
                } else {
                    changePasswordViewModel.checkId(
                        accountId = id.trim(),
                    )
                    focusManager.clearFocus()
                }
            }
        }
    }*/
}
