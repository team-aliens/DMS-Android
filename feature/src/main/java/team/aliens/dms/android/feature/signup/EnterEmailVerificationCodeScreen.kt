package team.aliens.dms.android.feature.signup

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.designsystem.VerificationCodeInput
import team.aliens.dms.android.core.designsystem.VerificationCodeInputDefaults
import team.aliens.dms.android.core.ui.Banner
import team.aliens.dms.android.core.ui.BannerDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.signup.navigation.SignUpNavigator

// Millisecond * Second * Minutes
private const val TIMER_TOTAL_SECONDS: Long = 1000 * 60 * 3
private const val TIMER_INTERVAL: Long = 1000

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun SignUpEnterEmailVerificationCodeScreen(
    modifier: Modifier = Modifier,
    navigator: SignUpNavigator,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    var timerText: String by remember { mutableStateOf("") }
    val toast = LocalToast.current
    val context = LocalContext.current
    var isVerificationInputAvailable by remember { mutableStateOf(true) }
    var isSessionExpired by remember { mutableStateOf(false) }
    val timer: CountDownTimer = remember {
        object : CountDownTimer(TIMER_TOTAL_SECONDS, TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / (1000 * 60)
                val seconds = millisUntilFinished / 1000 % 60
                timerText = context.getString(R.string.format_timer_m_s, minutes, seconds)
            }

            override fun onFinish() {
                isVerificationInputAvailable = false
                isSessionExpired = true
                viewModel.postIntent(SignUpIntent.UpdateEmailVerificationCode(value = ""))
            }
        }
    }

    LaunchedEffect(uiState.emailVerificationCode) {
        if (uiState.emailVerificationCode.length == SignUpViewModel.EMAIL_VERIFICATION_CODE_LENGTH) {
            viewModel.postIntent(SignUpIntent.CheckEmailVerificationCode)
        }
    }

    LaunchedEffect(uiState.sessionId) {
        timer.start()
    }

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SignUpSideEffect.EmailVerificationCodeChecked -> {
                timer.cancel()
                navigator.openSetId()
            }

            SignUpSideEffect.EmailVerificationCodeIncorrect -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_enter_email_verification_code_error_verification_code_incorrect),
            )

            SignUpSideEffect.EmailVerificationTooManyRequest -> toast.showErrorToast(
                message = context.getString(R.string.reset_password_account_verification_error_too_many_request),
            )

            SignUpSideEffect.EmailVerificationSessionReset -> {
                isVerificationInputAvailable = true
                with(timer) {
                    cancel()
                    start()
                }
                toast.showSuccessToast(
                    message = context.getString(R.string.sign_up_enter_email_verification_code_success_resent_email),
                )
            }

            SignUpSideEffect.EmailVerificationSessionResetFailed -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_enter_email_verification_code_error_cannot_resend_verification_code),
            )

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
                    IconButton(onClick = navigator::popUpToEnterEmail) {
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
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(BannerDefaults.DefaultTopSpace)
                    .startPadding(),
                message = {
                    BannerDefaults.DefaultText(text = stringResource(id = R.string.sign_up_enter_email_verification_code))
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            VerificationCodeInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                totalLength = SignUpViewModel.EMAIL_VERIFICATION_CODE_LENGTH,
                text = uiState.emailVerificationCode,
                onValueChange = { verificationCode ->
                    viewModel.postIntent(SignUpIntent.UpdateEmailVerificationCode(value = verificationCode))
                },
                supportingText = {
                    VerificationCodeInputDefaults.SupportingText(
                        text = stringResource(id = R.string.sign_up_enter_email_verification_code_please_enter_6_digit_code_sent_to_email),
                    )
                },
                enabled = isVerificationInputAvailable,
            )
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = if (isVerificationInputAvailable) {
                    timerText
                } else {
                    stringResource(
                        id = if (isSessionExpired) {
                            R.string.sign_up_enter_email_verification_code_error_timeout
                        } else {
                            R.string.sign_up_enter_email_verification_code_error_verification_code_incorrect
                        },
                    )
                },
                style = DmsTheme.typography.caption,
                color = if (isVerificationInputAvailable) {
                    DmsTheme.colorScheme.primary
                } else {
                    DmsTheme.colorScheme.error
                },
            )
            Spacer(modifier = Modifier.weight(3f))
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                onClick = { viewModel.postIntent(SignUpIntent.ResetEmailVerificationSession) },
                colors = ButtonDefaults.textGrayButtonColors(),
            ) {
                Text(text = stringResource(id = R.string.sign_up_enter_email_verification_code_resend_verification_code))
            }
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { viewModel.postIntent(SignUpIntent.CheckEmailVerificationCode) },
                enabled = uiState.emailVerificationCode.length == SignUpViewModel.EMAIL_VERIFICATION_CODE_LENGTH,
            ) {
                Text(text = stringResource(id = R.string.verify))
            }
        }
    }
}
