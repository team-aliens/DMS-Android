package team.aliens.dms.android.feature.editpassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator

@Destination
@Composable
internal fun ConfirmPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: EditPasswordNavigator,
) {
    // val viewModel: EditPasswordViewModel = hiltViewModel()
/*
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val toast = rememberToast()

    var password by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    val onPasswordChange = { value: String ->
        if (value.length != password.length) isError = false
        password = value
        changePasswordViewModel.setCurrentPassword(password)
    }

    LaunchedEffect(Unit) {
        changePasswordViewModel.editPasswordEffect.collect {
            when (it) {
                is ChangePasswordViewModel.Event.ComparePasswordSuccess -> navigator.openEditPasswordSetPasswordNav()

                is ChangePasswordViewModel.Event.UnauthorizedException -> {
                    isError = true
                }

                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        ),
                    )
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
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
        Column(
            modifier = Modifier.padding(
                top = 46.dp,
                start = 16.dp,
                end = 16.dp,
            )
        ) {

            AppLogo(
                darkIcon = isSystemInDarkTheme(),
            )
            Space(space = 32.dp)
            Body2(
                text = stringResource(id = R.string.OriginPw),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.84f)
                    .padding(top = 80.dp)
            ) {
                DormTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    isPassword = true,
                    hint = stringResource(id = R.string.Password),
                    errorDescription = stringResource(id = R.string.CheckPassword),
                    error = isError,
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    })
            }
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = (password.isNotEmpty() && !isError),
            ) {
                changePasswordViewModel.comparePassword()
            }
        }
    }*/
}
