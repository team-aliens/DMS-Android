package team.aliens.dms.android.feature.editpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.AppLogo
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun ConfirmPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: EditPasswordViewModel = hiltViewModel(),
    navigator: EditPasswordNavigator,
) {
    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.edit_password)) },
                navigationIcon = {
                    IconButton(onClick = navigator::popBackStack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier.padding(padValues),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding(),
            )
            Spacer(modifier = Modifier.weight(1f))
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = "asdf",
                onValueChange = {},
                passwordShowing = true,
                onPasswordShowingChange = {},
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }

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

@Composable
private fun Banner(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
    ) {
        AppLogo(
            modifier = Modifier.padding(start = PaddingDefaults.Large),
        )
        Text(
            modifier = Modifier.padding(start = PaddingDefaults.Large),
            text = stringResource(R.string.edit_password_old_password),
            style = DmsTheme.typography.body2,
        )
    }
}
