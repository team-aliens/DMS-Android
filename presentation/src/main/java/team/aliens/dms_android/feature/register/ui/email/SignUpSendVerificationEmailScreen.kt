package team.aliens.dms_android.feature.register.ui.email

import android.content.Context
import android.util.Patterns
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.DmsRoute
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.presentation.R

@Composable
fun SignUpSendVerificationEmailScreen(
    navController: NavController,
    registerEmailViewModel: RegisterEmailViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }

    val toast = rememberToast()

    var isError by remember { mutableStateOf(false) }
    var errorDescription by remember { mutableStateOf("") }

    var isPressedBackButton by remember { mutableStateOf(false) }

    val pattern = Patterns.EMAIL_ADDRESS

    val onEmailChange = { value: String ->
        if (value.length != email.length) isError = false
        email = value
    }

    if (isPressedBackButton) {
        DormCustomDialog(onDismissRequest = { /*TODO*/ }) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.FinishSignUp),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = {
                    navController.navigate(DmsRoute.Auth.SignIn) {
                        popUpTo(DmsRoute.Auth.SignIn) {
                            inclusive = true
                        }
                    }
                },
                onSubBtnClick = { isPressedBackButton = false },
            )
        }
    }


    BackHandler(enabled = true) {
        isPressedBackButton = true
    }

    LaunchedEffect(Unit) {
        registerEmailViewModel.registerEmailEvent.collect {
            when (it) {
                is RegisterEmailEvent.AllowEmail -> {
                    registerEmailViewModel.requestEmailCode(
                        email,
                        EmailVerificationType.SIGNUP,
                    )
                }
                is RegisterEmailEvent.ConflictException -> {
                    isError = true
                    errorDescription = context.getString(R.string.ConflictEmail)
                }
                is RegisterEmailEvent.SendEmailSuccess -> {
                    navController.currentBackStackEntry?.arguments?.run {
                        putString(
                            "schoolCode",
                            navController.previousBackStackEntry?.arguments?.getString("schoolCode")
                        )
                        putString(
                            "schoolId",
                            navController.previousBackStackEntry?.arguments?.getString("schoolId")
                        )
                        putString(
                            "schoolAnswer",
                            navController.previousBackStackEntry?.arguments?.getString("schoolAnswer")
                        )
                        putString("email", email)
                    }
                    navController.navigate(DmsRoute.SignUp.CheckEmailVerificationCode)
                }
                is RegisterEmailEvent.TooManyRequestsException -> {
                    toast(context.getString(R.string.ChangeEmail))
                }
                else -> toast(
                    getStringFromEvent(
                        context = context,
                        event = it,
                    )
                )
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
        Column(modifier = Modifier.fillMaxHeight(0.843f)) {
            AppLogo(
                darkIcon = isSystemInDarkTheme(),
            )
            Space(space = 8.dp)
            Body2(text = stringResource(id = R.string.EnterEmailAddress))
            Space(space = 86.dp)
            DormTextField(
                value = email,
                onValueChange = onEmailChange,
                hint = stringResource(id = R.string.EnterEmailAddress),
                error = isError,
                keyboardType = KeyboardType.Email,
                errorDescription = errorDescription,
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                imeAction = ImeAction.Done,
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.SendVerificationCode),
            color = DormButtonColor.Blue,
            enabled = (email.isNotEmpty() && !isError),
        ) {
            if (pattern.matcher(email).find()) {
                registerEmailViewModel.checkEmailDuplicate(email.trim())
            } else {
                isError = true
                errorDescription = context.getString(R.string.NotValidEmailFormat)
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: RegisterEmailEvent,
): String = when (event) {
    is RegisterEmailEvent.BadRequestException -> {
        context.getString(R.string.BadRequest)
    }
    is RegisterEmailEvent.TooManyRequestsException -> {
        context.getString(R.string.EmailTooManyRequest)
    }
    is RegisterEmailEvent.InternalServerException -> {
        context.getString(R.string.ServerException)
    }
    else -> {
        context.getString(R.string.UnKnownException)
    }

}