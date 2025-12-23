package team.aliens.dms.android.feature.resetpassword

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.AlertDialog
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavigator

@Suppress("ConstPropertyName")
private const val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ResetPasswordSetPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: ResetPasswordNavigator,
    viewModel: ResetPasswordViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val (showPassword, onShowPasswordChange) = remember { mutableStateOf(false) }
    val (showPasswordRepeat, onShowPasswordRepeatChange) = remember { mutableStateOf(false) }

    val toast = LocalToast.current
    val context = LocalContext.current

    val (shouldShowQuitSignUpDialog, onShouldShowQuitSignUpDialogChange) = remember {
        mutableStateOf(false)
    }

    if (shouldShowQuitSignUpDialog) {
        AlertDialog(
            text = { Text(text = stringResource(id = R.string.reset_password_set_password_password_success_changed)) },
            onDismissRequest = { /* explicit blank */ },
            confirmButton = {
                TextButton(
                    onClick = navigator::openSignIn,
                ) {
                    Text(text = stringResource(id = R.string.reset_password_go_to_sign_in_screen))
                }
            },
        )
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            ResetPasswordSideEffect.PasswordReset -> onShouldShowQuitSignUpDialogChange(true)
            ResetPasswordSideEffect.PasswordMismatch -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_set_password_error_password_mismatch),
            )

            ResetPasswordSideEffect.InvalidPassword -> {
                toast.showErrorToast(
                    message = context.getString(R.string.sign_up_set_password_invalid_password),
                )
            }

            else -> { /* explicit blank */
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.openSignIn() }) {
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
                    .topPadding(BannerDefaults.DefaultTopSpace)
                    .startPadding(),
                message = { BannerDefaults.DefaultText(text = stringResource(id = R.string.edit_password_new_password)) },
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
                    value = uiState.newPassword,
                    onValueChange = {
                        viewModel.postIntent(
                            ResetPasswordIntent.UpdateNewPassword(
                                value = it,
                            ),
                        )
                    },
                    passwordShowing = showPassword,
                    onPasswordShowingChange = onShowPasswordChange,
                    hintText = stringResource(id = R.string.edit_password_please_enter_new_password),
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    value = uiState.newPasswordRepeat,
                    onValueChange = {
                        viewModel.postIntent(
                            ResetPasswordIntent.UpdateNewPasswordRepeat(
                                value = it,
                            ),
                        )
                    },
                    passwordShowing = showPasswordRepeat,
                    onPasswordShowingChange = onShowPasswordRepeatChange,
                    hintText = stringResource(id = R.string.edit_password_please_enter_new_password_repeat),
                )
            }
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(ResetPasswordIntent.SetPassword) },
                enabled = uiState.newPassword.isNotEmpty() && uiState.newPasswordRepeat.isNotEmpty(),
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
    BackHandler {
        onShouldShowQuitSignUpDialogChange(true)
    }
}
