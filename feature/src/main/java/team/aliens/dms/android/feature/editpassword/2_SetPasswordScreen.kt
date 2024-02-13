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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
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
    viewModel: EditPasswordViewModel = hiltViewModel(),
    currentPassword: String,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val (showNewPassword, onShowNewPasswordChange) = remember { mutableStateOf(false) }
    val (showNewPasswordRepeat, onShowNewPasswordRepeatChange) = remember { mutableStateOf(false) }

    val isSetPasswordAvailable by remember(
        key1 = uiState.newPassword,
        key2 = uiState.newPasswordRepeat,
    ) {
        mutableStateOf(
            checkSetPasswordAvailable(
                newPassword = uiState.newPassword,
                newPasswordRepeat = uiState.newPasswordRepeat,
            ),
        )
    }

    val toast = LocalToast.current
    val context = LocalContext.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            EditPasswordSideEffect.SetPasswordPasswordEdited -> {
                navigator.navigateUp()
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

    LaunchedEffect(currentPassword) {
        viewModel.postIntent(EditPasswordIntent.UpdateCurrentPassword(value = currentPassword))
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.edit_password)) },
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
            modifier = Modifier.padding(padValues),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
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
                onValueChange = { value ->
                    viewModel.postIntent(EditPasswordIntent.UpdateNewPasswordRepeat(value))
                },
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
                onClick = {
                    viewModel.postIntent(EditPasswordIntent.SetPassword)
                },
                enabled = isSetPasswordAvailable,
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

private fun checkSetPasswordAvailable(
    newPassword: String,
    newPasswordRepeat: String,
): Boolean {
    if (newPassword.isBlank()) {
        return false
    }
    if (newPasswordRepeat.isBlank()) {
        return false
    }
    return newPassword == newPasswordRepeat
}
