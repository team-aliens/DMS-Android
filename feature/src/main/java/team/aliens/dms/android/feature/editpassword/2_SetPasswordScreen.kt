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
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun EditPasswordSetPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: EditPasswordNavigator,
    viewModel: EditPasswordViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val (showNewPassword, onShowNewPasswordChange) = remember { mutableStateOf(false) }
    val (showNewPasswordRepeat, onShowNewPasswordRepeatChange) = remember { mutableStateOf(false) }

    val isSetPasswordAvailable by remember(uiState.newPassword, uiState.newPasswordRepeat) {
        mutableStateOf(
            false// TODO
        )
    }

    val (shouldShowQuitSignUpDialog, onShouldShowQuitSignUpDialogChange) = remember {
        mutableStateOf(false)
    }
    if (shouldShowQuitSignUpDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.edit_password_quitting_edit_password)) },
            text = { Text(text = stringResource(id = R.string.edit_password_info_are_you_sure_you_quit_edit_password)) },
            onDismissRequest = { /* explicit blank */ },
            confirmButton = {
                TextButton(
                    onClick = navigator::popUpToMain,
                ) {
                    Text(text = stringResource(id = R.string.accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onShouldShowQuitSignUpDialogChange(false) },
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
        )
    }
    val toast = LocalToast.current
    val context = LocalContext.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            EditPasswordSideEffect.SetPasswordPasswordEdited -> {
                navigator.popUpToMain()
                toast.showSuccessToast(
                    message = context.getString(R.string.edit_password_success_password_has_set),
                )
            }

            EditPasswordSideEffect.SetPasswordPasswordIncorrect -> toast.showErrorToast(
                message = context.getString(R.string.edit_password_error_password_mismatch),
            )

            else -> {/* explicit blank */
            }
        }
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.edit_password)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onShouldShowQuitSignUpDialogChange(true)
                        },
                    ) {
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
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(
                        text = stringResource(id = R.string.edit_password_new_password),
                    )
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = uiState.newPassword,
                onValueChange = { value ->
                    viewModel.postIntent(EditPasswordIntent.UpdateNewPassword(value))
                },
                passwordShowing = showNewPassword,
                onPasswordShowingChange = onShowNewPasswordChange,
                hintText = stringResource(id = R.string.edit_password_please_enter_new_password)
            )
            PasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = uiState.newPasswordRepeat,
                onValueChange = { viewModel.postIntent(EditPasswordIntent.UpdateNewPasswordRepeat(it)) },
                passwordShowing = showNewPasswordRepeat,
                onPasswordShowingChange = onShowNewPasswordRepeatChange,
                hintText = stringResource(id = R.string.edit_password_please_enter_new_password_repeat)
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(EditPasswordIntent.SetPassword) },
                enabled = isSetPasswordAvailable,
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}
