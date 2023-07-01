package team.aliens.dms_android.feature.signup.ui.email

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.dms_android.util.VerifyTime
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.presentation.R

@Composable
internal fun VerifyEmailScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel,
) {

    val state by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val onVerificationCodeChange = { authCode: String ->
        signUpViewModel.postIntent(
            SignUpIntent.VerifyEmail.SetAuthCode(
                authCode = authCode,
            )
        )
    }

    var time by remember { mutableStateOf("3 : 00") }

    var isRunningTimer by remember { mutableStateOf(true) }

    var isPressedBackButton by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        isPressedBackButton = true
    }

    if (isPressedBackButton) {
        DormCustomDialog(onDismissRequest = { /*TODO*/ }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.CancelEmailVerify),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = {
                    navController.navigate(DmsRoute.SignUp.SendVerificationEmail) {
                        popUpTo(DmsRoute.SignUp.SendVerificationEmail) {
                            inclusive = true
                        }
                    }
                },
                onSubBtnClick = { isPressedBackButton = false },
            )
        }
    }

    // FixMe too dirty
    LaunchedEffect(isRunningTimer) {
        run loop@{
            var totalSecond = 0
            var minutes = 0
            var seconds = ""
            repeat(VerifyTime.START_TIME) {
                if (!isRunningTimer) {
                    isRunningTimer = true
                    return@loop
                }
                delay(1000L)
                totalSecond = VerifyTime.START_TIME - 1 - it
                minutes = totalSecond / VerifyTime.DIVISION_TIME
                seconds = (totalSecond % VerifyTime.DIVISION_TIME).toString()
                if (seconds.toInt() < VerifyTime.REMAIN_TIME) seconds = seconds.padStart(2, '0')
                time = "$minutes : $seconds"
            }
        }
    }

    Column(
        modifier = Modifier
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
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(text = stringResource(id = R.string.VerificationCode))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 100.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = state.authCode,
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
                                        color = if (state.authCode.length - 1 >= index) DormTheme.colors.primaryVariant
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
                    id = if (state.authCodeMismatchError) R.string.sign_up_email_error_verification_code_incorrect
                    else R.string.sign_up_email_please_enter_verification_code,
                ),
                color = if (state.authCodeMismatchError) DormTheme.colors.error
                else DormTheme.colors.primaryVariant,
            )
            Space(space = 10.dp)
            Body3(
                text = time,
                color = DormTheme.colors.primary,
            )
            RatioSpace(height = 0.649f)
            ButtonText(
                modifier = Modifier.dormClickable(
                    rippleEnabled = false,
                ) {
                    isRunningTimer = false
                    signUpViewModel.postIntent(SignUpIntent.SendEmail.SendEmailVerificationCode)
                },
                text = stringResource(id = R.string.ResendVerificationCode),
            )
            Space(space = 26.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = state.authCodeConfirmButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.VerifyEmail.CheckEmailVerificationCode)
            }
        }
    }
}
