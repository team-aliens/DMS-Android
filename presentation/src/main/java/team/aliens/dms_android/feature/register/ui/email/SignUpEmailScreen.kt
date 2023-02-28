package team.aliens.dms_android.feature.register.ui.email

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
            when(it){
                is RegisterEmailEvent.AllowEmail -> {
                    registerEmailViewModel.requestEmailCode(email)
                }
                is RegisterEmailEvent.ConflictException -> {
                    isError = true
                    errorDescription = context.getString(R.string.ConflictEmail)
                }
                is RegisterEmailEvent.SendEmailSuccess -> {
                    navController.navigate("")
                }
                is RegisterEmailEvent.BadRequestException -> {
                    toast(context.getString(R.string.BadRequest))
                }
                is RegisterEmailEvent.TooManyRequestsException -> {
                    toast(context.getString(R.string.TooManyRequest))
                }
                is RegisterEmailEvent.InternalServerException -> {
                    toast(context.getString(R.string.ServerException))
                }
                else -> toast(context.getString(R.string.UnKnownException))
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
        AppLogo()
        Spacer(modifier = Modifier.height(8.dp))
        Body2(
            text = stringResource(id = R.string.EnterEmailAddress)
        )
        Spacer(modifier = Modifier.height(60.dp))
        DormTextField(
            value = email,
            onValueChange = onEmailChange,
            hint = stringResource(id = R.string.EnterEmailAddress)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.796f))
        DormContainedLargeButton(
            text = stringResource(id = R.string.SendVerificationCode),
            color = DormButtonColor.Blue,
            enabled = email.isNotEmpty(),
        ) {
            registerEmailViewModel.checkEmailDuplicate(email)
        }
    }
}