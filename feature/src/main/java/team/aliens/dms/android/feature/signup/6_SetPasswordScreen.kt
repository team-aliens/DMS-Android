package team.aliens.dms.android.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun SignUpSetPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    // signUpViewModel: SignUpViewModel,
) {
    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(
                        text = stringResource(id = R.string.sign_up_set_password),
                    )
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
            ) {
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    value = "", // uiState.newPassword,
                    onValueChange = { value ->
                        // viewModel.postIntent(EditPasswordIntent.UpdateNewPassword(value))
                    },
                    passwordShowing = false, // showNewPassword,
                    onPasswordShowingChange = {}, // onShowNewPasswordChange,
                    hintText = stringResource(id = R.string.sign_up_enter_password)
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    value = "", // uiState.newPassword,
                    onValueChange = { value ->
                        // viewModel.postIntent(EditPasswordIntent.UpdateNewPassword(value))
                    },
                    passwordShowing = false, // showNewPassword,
                    onPasswordShowingChange = {}, // onShowNewPasswordChange,
                    hintText = stringResource(id = R.string.sign_up_enter_password_repeat)
                )
            }
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                // FIXME: 서버 연동
                onClick = navigator::openSignUpEnterEmailVerificationCode,
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
    /*
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
        }*/
}