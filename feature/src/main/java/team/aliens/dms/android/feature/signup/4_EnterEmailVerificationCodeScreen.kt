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
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
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
                timerText = context.getString(R.string.format_timer, minutes, seconds)
            }

            override fun onFinish() {
                isVerificationInputAvailable = false
                isSessionExpired = true
                viewModel.postIntent(SignUpIntent.UpdateEmailVerificationCode(value = ""))
            }
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

            SignUpSideEffect.EmailVerificationSessionReset -> {
                isVerificationInputAvailable = true
                with(timer) {
                    cancel()
                    start()
                }
            }

            SignUpSideEffect.EmailVerificationSessionResetFailed -> toast.showErrorToast(
                message = context.getString(R.string.sign_up_enter_email_verification_code_error_cannot_resend_email),
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
                    VerificationCodeInputDefaults.SupportingText(text = stringResource(id = R.string.sign_up_enter_email_verification_code_please_enter_6_digit_code_sent_to_email))
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

    /*

    val uiState by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    val localToast = LocalToast.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        signUpViewModel.postIntent(SignUpIntent.SendEmail.SetEmailTimerWorked(emailTimerWorked = true))
        signUpViewModel.sideEffectFlow.collect {
            when (it) {
                is SignUpSideEffect.VerifyEmail.SuccessVerifyEmail -> {
                    navigator.openSetId()
                }

                is SignUpSideEffect.VerifyEmail.ConflictEmail -> {
                    localToast.showErrorToast(context.getString(R.string.sign_up_email_error_conflict))
                }

                else -> {}
            }
        }
    }

    val onVerificationCodeChange = { authCode: String ->
        if (authCode.length <= 6) {
            signUpViewModel.postIntent(
                SignUpIntent.VerifyEmail.SetAuthCode(
                    authCode = authCode,
                )
            )
            if (authCode.length == 6) {
                focusManager.clearFocus()
                signUpViewModel.postIntent(SignUpIntent.VerifyEmail.CheckEmailVerificationCode)
            }
        }
    }

    var isPressedBackButton by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        isPressedBackButton = true
    }

    if (isPressedBackButton) {
        DormCustomDialog(onDismissRequest = { *//*TODO*//* }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.CancelEmailVerify),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = {
                    navigator.openEnterEmail(clearStack = true)
                    signUpViewModel.postIntent(
                        SignUpIntent.VerifyEmail.SetAuthCode(
                            authCode = "",
                        )
                    )
                },
                onSubBtnClick = { isPressedBackButton = false },
            )
        }
    }

    LaunchedEffect(uiState.emailTimerWorked) {
        var min: String
        var sec: String
        repeat(TotalSecond) {
            min = ((TotalSecond - it) / 60).toString()
            sec = ((TotalSecond - it) % 60).toString().padStart(2, '0')
            signUpViewModel.postIntent(SignUpIntent.SendEmail.SetEmailExpirationTime("$min : $sec"))
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.surface,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(108.dp))
            AppLogo(darkIcon = isSystemInDarkTheme())
            Space(space = 8.dp)
            Body2(text = stringResource(id = R.string.VerificationCode))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            BasicTextField(
                value = uiState.authCode,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                decorationBox = {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        items(6) { index ->
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = if (uiState.authCode.length - 1 >= index) DormTheme.colors.primaryVariant
                                        else DormTheme.colors.secondaryVariant,
                                    ),
                            )
                        }
                    }
                },
            )
            Space(space = 40.dp)
            Body3(
                text = stringResource(
                    id = if (uiState.authCodeMismatchError) R.string.sign_up_email_error_verification_code_incorrect
                    else R.string.sign_up_email_please_enter_verification_code,
                ),
                color = if (uiState.authCodeMismatchError) DormTheme.colors.error
                else DormTheme.colors.primaryVariant,
            )
            Space(space = 10.dp)
            Body3(
                text = uiState.emailExpirationTime,
                color = DormTheme.colors.primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonText(
                modifier = Modifier.dormClickable(
                    rippleEnabled = false,
                ) {
                    signUpViewModel.postIntent(
                        SignUpIntent.SendEmail.SetEmailTimerWorked(
                            emailTimerWorked = false
                        )
                    )
                    signUpViewModel.postIntent(SignUpIntent.SendEmail.SendEmailVerificationCode)
                },
                text = stringResource(id = R.string.ResendVerificationCode),
            )
            Space(space = 26.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = uiState.authCodeButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.VerifyEmail.CheckEmailVerificationCode)
            }
            Spacer(modifier = Modifier.height(82.dp))
        }
    }*/
}
