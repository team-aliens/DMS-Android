package team.aliens.dms_android.feature.register.ui.email

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel
import team.aliens.presentation.R

@Composable
fun SignUpEmailScreen(
    navController: NavController,
    registerEmailViewModel: RegisterEmailViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }

    val toast = rememberToast()

    var isError by remember { mutableStateOf(false) }
    var errorDescription by remember { mutableStateOf("") }

    val onEmailChange = { value: String ->
        email = value
    }

    LaunchedEffect(Unit) {
        registerEmailViewModel.registerEmailEvent.collect {
            when (it) {
                is RegisterEmailEvent.AllowEmail -> {
                    registerEmailViewModel.requestEmailCode(email)
                }
                is RegisterEmailEvent.ConflictException -> {
                    isError = true
                    errorDescription = context.getString(R.string.ConflictEmail)
                }
                is RegisterEmailEvent.SendEmailSuccess -> {
                    navController.currentBackStackEntry?.arguments?.run {
                        putString("schoolCode", navController.previousBackStackEntry?.arguments?.getString("schoolCode"))
                        putString("schoolId", navController.previousBackStackEntry?.arguments?.getString("schoolId"))
                        putString("schoolAnswer", navController.previousBackStackEntry?.arguments?.getString("schoolAnswer"))
                        putString("email", email)
                    }
                    navController.navigate(NavigationRoute.SignUpEmailVerify)
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
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.843f)
        ) {
            AppLogo()
            Spacer(modifier = Modifier.height(8.dp))
            Body2(
                text = stringResource(id = R.string.EnterEmailAddress)
            )
            Spacer(modifier = Modifier.height(60.dp))
            DormTextField(
                value = email,
                onValueChange = onEmailChange,
                hint = stringResource(id = R.string.EnterEmailAddress),
                error = isError,
                errorDescription = errorDescription,
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.SendVerificationCode),
            color = DormButtonColor.Blue,
            enabled = email.isNotEmpty(),
        ) {
            registerEmailViewModel.checkEmailDuplicate(email)
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: RegisterEmailEvent,
): String = when(event){
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