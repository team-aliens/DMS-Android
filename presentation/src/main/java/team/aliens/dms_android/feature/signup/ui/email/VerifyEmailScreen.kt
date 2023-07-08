package team.aliens.dms_android.feature.signup.ui.email

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import kotlinx.coroutines.delay
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.LocalToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.signup.SignUpIntent
import team.aliens.dms_android.feature.signup.SignUpSideEffect
import team.aliens.dms_android.feature.signup.SignUpViewModel
import team.aliens.presentation.R

private const val TotalSecond = 180

@Composable
internal fun VerifyEmailScreen(
    onNavigateToSetId: () -> Unit,
    onNavigateToSetEmailWithInclusive: () -> Unit,
    signUpViewModel: SignUpViewModel,
) {

    val state by signUpViewModel.stateFlow.collectAsStateWithLifecycle()

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
                    onNavigateToSetId()
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
        DormCustomDialog(onDismissRequest = { /*TODO*/ }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.CancelEmailVerify),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = {
                    onNavigateToSetEmailWithInclusive()
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

    LaunchedEffect(state.emailTimerWorked) {
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
                text = state.emailExpirationTime,
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
                enabled = state.authCodeConfirmButtonEnabled,
            ) {
                signUpViewModel.postIntent(SignUpIntent.VerifyEmail.CheckEmailVerificationCode)
            }
            Spacer(modifier = Modifier.height(82.dp))
        }
    }
}
