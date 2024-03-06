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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
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
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val (showPassword, onShowPasswordChange) = remember { mutableStateOf(false) }
    val (showPasswordRepeat, onShowPasswordRepeatChange) = remember { mutableStateOf(false) }

    val toast = LocalToast.current
    val context = LocalContext.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            // FIXME: 이미지 업로드 구현
            SignUpSideEffect.PasswordSet -> navigator.openTerms()
            SignUpSideEffect.PasswordMismatch -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_set_password_error_password_mismatch),
            )

            else -> {/* explicit blank */
            }
        }
    }

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
                message = { BannerDefaults.DefaultText(text = stringResource(id = R.string.sign_up_set_password)) },
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
                    value = uiState.password,
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdatePassword(value = it)) },
                    passwordShowing = showPassword,
                    onPasswordShowingChange = onShowPasswordChange,
                    hintText = stringResource(id = R.string.sign_up_set_password_please_enter_password)
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    value = uiState.passwordRepeat,
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdatePasswordRepeat(value = it)) },
                    passwordShowing = showPasswordRepeat,
                    onPasswordShowingChange = onShowPasswordRepeatChange,
                    hintText = stringResource(id = R.string.sign_up_set_password_please_enter_password_again)
                )
            }
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignUpIntent.ConfirmPassword) },
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}