package team.aliens.dms.android.feature.signup

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
import team.aliens.dms.android.core.designsystem.DmsIcon
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
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

// FIXME 비밀번호 정규식 검사
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

    val (shouldShowQuitSignUpDialog, onShouldShowQuitSignUpDialogChange) = remember {
        mutableStateOf(false)
    }

    val (isPasswordInvalid, onChangeIsPasswordInvalid) = remember(
        uiState.password,
        uiState.passwordRepeat,
    ) {
        mutableStateOf(false)
    }

    if (shouldShowQuitSignUpDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.sign_up)) },
            text = { Text(text = stringResource(id = R.string.sign_up_info_are_you_sure_you_quit_sign_up)) },
            onDismissRequest = { /* explicit blank */ },
            confirmButton = {
                TextButton(
                    onClick = navigator::popUpToSignUp,
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

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            // FIXME: 이미지 업로드 구현
            SignUpSideEffect.PasswordSet -> navigator.openSetProfileImage()
            SignUpSideEffect.PasswordMismatch -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_set_password_error_password_mismatch),
            )

            SignUpSideEffect.InvalidPassword -> {
                toast.showErrorToast(
                    message = context.getString(R.string.sign_up_set_password_invalid_password),
                )
                onChangeIsPasswordInvalid(true)
            }

            else -> { /* explicit blank */
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigator::popUpToSetId) {
                        Icon(
                            painter = painterResource(id = DmsIcon.Back),
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
                    hintText = stringResource(id = R.string.sign_up_set_password_please_enter_password),
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    value = uiState.passwordRepeat,
                    onValueChange = { viewModel.postIntent(SignUpIntent.UpdatePasswordRepeat(value = it)) },
                    passwordShowing = showPasswordRepeat,
                    onPasswordShowingChange = onShowPasswordRepeatChange,
                    hintText = stringResource(id = R.string.sign_up_set_password_please_enter_password_again),
                )
            }
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignUpIntent.ConfirmPassword) },
                enabled = uiState.password.isNotEmpty() && uiState.passwordRepeat.isNotEmpty(),
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
    BackHandler {
        onShouldShowQuitSignUpDialogChange(true)
    }
}
