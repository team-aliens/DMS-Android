package team.aliens.dms.android.feature.signup

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.AlertDialog
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.designsystem.TextField
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator
import team.aliens.dms.android.shared.validator.checkIfEmailValid

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun EnterEmailScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val toast = LocalToast.current
    val context = LocalContext.current

    var isEmailFormatError by rememberSaveable(uiState.email) { mutableStateOf(false) }
    val emailValid by remember(uiState.email) {
        mutableStateOf(
            uiState.email.run {
                if (length < 5) {
                    false
                } else {
                    checkIfEmailValid(uiState.email)
                }
            },
        )
    }

    // TODO: 중복 코드 제거, Sign Up navigation을 구현하여 해결 가능할 듯
    val (shouldShowQuitSignUpDialog, onShouldShowQuitSignUpDialogChange) = remember {
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
            SignUpSideEffect.EmailAvailable -> navigator.openSignUpEnterEmailVerificationCode()
            SignUpSideEffect.EmailFormatError -> {
                toast.showErrorToast(
                    message = context.getString(R.string.sign_up_enter_email_error_invalid_format),
                )
                isEmailFormatError = true
            }

            SignUpSideEffect.EmailNotAvailable -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_enter_email_error_email_not_available),
            )

            else -> {/* explicit blank */
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { onShouldShowQuitSignUpDialogChange(true) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues)
                .imePadding(),
        ) {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace)
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(text = stringResource(id = R.string.sign_up_enter_email))
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                value = uiState.email,
                hint = {
                    Text(text = stringResource(id = R.string.sign_up_enter_email))
                },
                onValueChange = { viewModel.postIntent(SignUpIntent.UpdateEmail(value = it)) },
                supportingText = if (isEmailFormatError) {
                    { Text(text = stringResource(id = R.string.sign_up_enter_email_error_invalid_format)) }
                } else {
                    null
                },
                isError = isEmailFormatError,
            )
            Spacer(modifier = Modifier.weight(3f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignUpIntent.VerifyEmail) },
                enabled = emailValid,
            ) {
                Text(text = stringResource(id = R.string.sign_up_send_email_verification_code))
            }
        }
    }
    BackHandler {
        onShouldShowQuitSignUpDialogChange(true)
    }
}
