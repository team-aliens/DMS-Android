package team.aliens.dms.android.feature.editpassword

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.rememberToastState
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.PasswordTextField
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.extension.collectInLaunchedEffectWithLifeCycle
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun ConfirmPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: EditPasswordNavigator,
    viewModel: EditPasswordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val toast = rememberToastState()
    val context = LocalContext.current

    val (showPassword, onShowPasswordChange) = remember { mutableStateOf(false) }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifeCycle { sideEffect ->
        when (sideEffect) {
            EditPasswordSideEffect.ConfirmPasswordPasswordConfirmed -> navigator.openEditPasswordSetPassword()
            EditPasswordSideEffect.ConfirmPasswordPasswordMismatch -> toast.showErrorToast(
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
                value = uiState.currentPassword,
                onValueChange = { value ->
                    viewModel.postIntent(EditPasswordIntent.UpdateCurrentPassword(value))
                },
                passwordShowing = showPassword,
                onPasswordShowingChange = onShowPasswordChange,
                hintText = stringResource(id = R.string.edit_password_please_enter_current_password)
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {
                    viewModel.postIntent(EditPasswordIntent.ConfirmPassword)
                },
                enabled = uiState.currentPassword.isNotBlank(),
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}
