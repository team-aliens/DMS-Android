package team.aliens.dms.android.feature.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavigator

@Suppress("ConstPropertyName")
private const val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"

@Destination
@Composable
fun ResetPasswordSetPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: ResetPasswordNavigator,
    // changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {/*

    val context = LocalContext.current

    val toast = rememberToast()

    val focusManager = LocalFocusManager.current

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var isPasswordFormatError by remember { mutableStateOf(false) }
    var isPasswordMatchError by remember { mutableStateOf(false) }

    var isShowDialog by remember { mutableStateOf(false) }
    var isPressedBackButton by remember { mutableStateOf(false) }

    val onPasswordChange = { passwordValue: String ->
        if (passwordValue.length != password.length) isPasswordFormatError = false
        password = passwordValue
    }

    val onRepeatPasswordChange = { repeatPasswordValue: String ->
        if (repeatPasswordValue.length != repeatPassword.length) isPasswordMatchError = false
        repeatPassword = repeatPasswordValue
    }

    BackHandler(enabled = true) {
        isPressedBackButton = true
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = DormTheme.colors.surface,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {

        TopBar(
            title = stringResource(R.string.ChangePassword),
            onPrevious = navigator::popBackStack,
        )

        if (isShowDialog) {
            DormCustomDialog(
                onDismissRequest = {},
            ) {
                DormSingleButtonDialog(
                    content = stringResource(id = R.string.SuccessChangePassword),
                    mainBtnText = stringResource(id = R.string.GoLogin),
                    onMainBtnClick = navigator::openSignIn,
                    mainBtnTextColor = DormColor.DormPrimary,
                )
            }

            Image(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 7.dp)
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(team.aliens.dms.android.designsystem.R.drawable.ic_information),
                contentDescription = stringResource(id = R.string.MainLogo),
            )

            Space(space = 1.dp)

            Body4(
                text = stringResource(id = R.string.SetNewPassword),
            )
        }

        if (isPressedBackButton) {
            DormCustomDialog(
                onDismissRequest = { *//*TODO*//* },
            ) {
                DormDoubleButtonDialog(
                    content = stringResource(id = R.string.FinishResetPassword),
                    mainBtnText = stringResource(id = R.string.Yes),
                    subBtnText = stringResource(id = R.string.No),
                    onMainBtnClick = navigator::openSignIn,
                    onSubBtnClick = { isPressedBackButton = false },
                )
            }
        }

        LaunchedEffect(Unit) {
            changePasswordViewModel.editPasswordEffect.collect {
                when (it) {
                    is ChangePasswordViewModel.Event.ResetPasswordSuccess -> {
                        toast(context.getString(R.string.SuccessResetPassword))
                        navigator.openSignIn()
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 38.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
        ) {
            AppLogo()
            Space(space = 8.dp)
            Body2(
                text = stringResource(id = R.string.ChangePassword),
            )
            Space(space = 4.dp)
            Caption(
                text = stringResource(id = R.string.PasswordWarning),
                color = DormColor.Gray500,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
            ) {
                Box(modifier = Modifier.height(76.dp)) {
                    DormTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        error = isPasswordFormatError,
                        isPassword = true,
                        hint = stringResource(id = R.string.ScanNewPassword),
                        errorDescription = stringResource(id = R.string.CheckPasswordFormat),
                        imeAction = ImeAction.Next,
                    )
                }
                Space(space = 7.dp)
                Box(
                    modifier = Modifier.height(76.dp),
                ) {
                    DormTextField(
                        value = repeatPassword,
                        onValueChange = onRepeatPasswordChange,
                        error = isPasswordMatchError,
                        isPassword = true,
                        hint = stringResource(id = R.string.CheckScanNewPassword),
                        errorDescription = stringResource(id = R.string.MismatchRepeatPassword),
                        keyboardActions = KeyboardActions {
                            focusManager.clearFocus()
                        },
                        imeAction = ImeAction.Done,
                    )
                }
            }
            RatioSpace(height = 0.742f)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Check),
                color = DormButtonColor.Blue,
                enabled = (password.isNotBlank() && repeatPassword.isNotBlank())
            ) {
                if (password != repeatPassword) {
                    isPasswordMatchError = true
                } else if (!Pattern.compile(passwordFormat).matcher(password).find()) {
                    isPasswordFormatError = true
                } else {
                    *//* todo
                        navController.previousBackStackEntry?.arguments?.run {
                        changePasswordViewModel.resetPassword(
                            accountId = getString("accountId").toString(),
                            emailVerificationCode = getString("authCode").toString(),
                            email = getString("email").toString(),
                            studentName = getString("name").toString(),
                            newPassword = password,
                        )
                    }*//*
                }
            }
        }
    }*/
}
