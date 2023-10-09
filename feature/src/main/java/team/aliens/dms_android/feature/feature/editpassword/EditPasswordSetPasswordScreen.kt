package team.aliens.dms_android.feature.feature.editpassword

import androidx.compose.runtime.Composable

@Composable
internal fun EditPasswordSetPasswordScreen(
    onNavigateToHome: () -> Unit,
) {

}

/*

import android.content.Context
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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
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
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordViewModel
import team.aliens.dms_android.feature.signup.RegisterEmailEvent
import team.aliens.dms_android.feature.signup.RegisterEmailViewModel
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.presentation.R

@Composable
fun EditPasswordSetPasswordScreen(
    //navController: NavController,
    registerEmailViewModel: RegisterEmailViewModel = hiltViewModel(),
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    var verificationCode by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    var isError by remember { mutableStateOf(false) }

    var isRunningTimer by remember { mutableStateOf(true) }

    val toast = rememberToast()

    var time by remember { mutableStateOf("3 : 00") }

    val state = changePasswordViewModel.state.collectAsState().value

    var email by remember { mutableStateOf("") }

    email = navController.previousBackStackEntry?.arguments?.getString("email").toString()

    val onVerificationCodeChange = { code: String ->
        if (code.length != verificationCode.length) isError = false
        if (code.length <= 6) {
            verificationCode = code
            if (code.length == 6) {
                focusManager.clearFocus()
                registerEmailViewModel.checkEmailCode(
                    email = email,
                    authCode = verificationCode,
                    type = EmailVerificationType.PASSWORD,
                )
            }
        } else {
            verificationCode = code.take(8)
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        registerEmailViewModel.registerEmailEvent.collect {
            when (it) {
                is RegisterEmailEvent.CheckEmailSuccess -> {
                    navController.currentBackStackEntry?.arguments?.run {
                        putString(
                            "accountId",
                            navController.previousBackStackEntry?.arguments?.getString("accountId")
                        )
                        putString(
                            "name",
                            navController.previousBackStackEntry?.arguments?.getString("name")
                        )
                        putString("email", email)
                        putString("authCode", verificationCode)
                    }
                    navController.navigate(DmsRoute.Auth.EditPassword)
                }

                is RegisterEmailEvent.SendEmailSuccess -> {
                    toast(context.getString(R.string.ResendEmail))
                }

                is RegisterEmailEvent.CheckEmailUnauthorized -> {
                    isError = true
                }
                is RegisterEmailEvent.TooManyRequestsException -> {
                    toast(context.getString(R.string.Retry))
                    navController.popBackStack()
                }
                else -> toast(
                    getStringFromEvent(
                        context = context,
                        event = it,
                    ),
                )
            }
        }
    }

    LaunchedEffect(isRunningTimer) {
        run loop@{
            repeat(180) {
                if (!isRunningTimer) {
                    isRunningTimer = true
                    return@loop
                }
                delay(1000L)
                val totalSecond = 179 - it
                val minutes = totalSecond / 60
                val seconds = totalSecond % 60
                time = "$minutes : $seconds"
                if (totalSecond == 0) {
                    toast(context.getString(R.string.AuthenticationTimeout))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(
            text = stringResource(id = R.string.Identification),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 108.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicTextField(
                value = verificationCode,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = onVerificationCodeChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                decorationBox = {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(6) { index ->
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shape = CircleShape)
                                    .background(
                                        color = if (verificationCode.length - 1 >= index) {
                                            DormColor.Gray600
                                        } else DormColor.Gray400,
                                    ),
                            )
                        }
                    }
                }
            )
            Space(space = 40.dp)
            Body3(
                text = if (isError) stringResource(id = R.string.NoSameCode)
                else stringResource(id = R.string.EmailSixCode),
                color = if (isError) DormColor.Error
                else DormColor.Gray500,
            )
            Space(space = 10.dp)
            Body3(
                text = time,
                color = DormTheme.colors.primary,
            )
            RatioSpace(height = 0.65f)
            ButtonText(
                modifier = Modifier
                    .dormClickable(
                        rippleEnabled = false,
                    ) {
                        isRunningTimer = false
                        registerEmailViewModel.requestEmailCode(
                            email = email,
                            type = EmailVerificationType.PASSWORD,
                        )
                    },
                text = stringResource(id = R.string.ResendVerificationCode),
            )
            Space(space = 26.dp)
            DormContainedLargeButton(
                text = stringResource(id = R.string.Verification),
                color = DormButtonColor.Blue,
                enabled = verificationCode.isNotEmpty() && isRunningTimer
            ) {
                registerEmailViewModel.checkEmailCode(
                    email = email,
                    authCode = verificationCode,
                    type = EmailVerificationType.PASSWORD,
                )
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: RegisterEmailEvent,
): String = when (event) {
    is RegisterEmailEvent.CheckEmailNotFound -> {
        context.getString(R.string.CertificationInfoNotFound)
    }
    is RegisterEmailEvent.BadRequestException -> {
        context.getString(R.string.NotFound)
    }
    is RegisterEmailEvent.ConflictException -> {
        context.getString(R.string.ConflictEmail)
    }
    is RegisterEmailEvent.InternalServerException -> {
        context.getString(R.string.ServerException)
    }
    else -> {
        context.getString(R.string.UnKnownException)
    }
}*/
