package team.aliens.dms_android.feature.auth.changepassword

import android.content.Context
import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.viewmodel.auth.register.email.RegisterEmailViewModel
import team.aliens.dms_android.viewmodel.changepw.ChangePasswordViewModel
import team.aliens.domain.enums.EmailType
import team.aliens.presentation.R

@Composable
fun IdentificationScreen(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
    registerEmailViewModel: RegisterEmailViewModel = hiltViewModel(),
) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val toast = rememberToast()

    val pattern = Patterns.EMAIL_ADDRESS

    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    var isIdError by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

    val onIdChange = { userId: String ->
        if (userId.length != id.length) isIdError = false
        id = userId
        changePasswordViewModel.setId(id)
    }

    val onNameChange = { userName: String ->
        if (userName.length != name.length) isNameError = false
        name = userName
        changePasswordViewModel.setName(name)
    }

    val onEmailChange = { value: String ->
        if (value.length != userEmail.length) isEmailError = false
        userEmail = value
        changePasswordViewModel.setEmail(value)
    }

    var emailResponse by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        changePasswordViewModel.editPasswordEffect.collect {
            when (it) {
                is ChangePasswordViewModel.Event.CheckIdSuccess -> {
                    emailResponse = it.email
                }
                is ChangePasswordViewModel.Event.NotFoundException -> {
                    isIdError = true
                }
                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = it,
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        registerEmailViewModel.registerEmailEvent.collect {
            when (it) {
                is RegisterEmailEvent.SendEmailSuccess -> {
                    navController.navigate(NavigationRoute.ChangePasswordVerifyEmail)
                }
                is RegisterEmailEvent.TooManyRequestsException -> {
                    toast(context.getString(R.string.ChangeEmail))
                }
                else -> toast(
                    getStringFromEmailEvent(
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
            .fillMaxHeight(0.8f)
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(8.dp))
        Body2(
            text = stringResource(id = R.string.Identification),
            color = DormColor.Gray600,
        )
        Column(
            modifier = Modifier
                .padding(top = 60.dp)
        ) {
            Box(
                modifier = Modifier.height(68.dp),
            ) {
                DormTextField(
                    value = id,
                    onValueChange = onIdChange,
                    hint = stringResource(id = R.string.EnterId),
                    error = isIdError,
                    errorDescription = stringResource(id = R.string.NotExistId),
                )
            }
            AnimatedVisibility(
                visible = emailResponse.isNotBlank()
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = DormColor.Gray200,
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp,
                            )
                    ) {
                        Body2(
                            text = stringResource(id = R.string.MatchEmailToId),
                        )
                        Body2(
                            modifier = Modifier.padding(
                                top = 8.dp,
                            ),
                            text = emailResponse,
                            color = DormColor.DormPrimary,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(68.dp)
                            .padding(top = 24.dp)
                    ) {
                        DormTextField(
                            value = name,
                            onValueChange = onNameChange,
                            hint = stringResource(id = R.string.EnterName)
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Box(
                        modifier = Modifier
                            .height(68.dp)
                    ) {
                        DormTextField(
                            value = userEmail,
                            onValueChange = onEmailChange,
                            hint = stringResource(id = R.string.EnterEmailAddress),
                            error = isEmailError,
                            errorDescription = context.getString(R.string.NotValidEmailFormat)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight(if (emailResponse.isEmpty()) 0.05f else 0.6f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = if (emailResponse.isEmpty()) id.isNotBlank() && !isIdError
                else (id.isNotBlank() && name.isNotBlank() && userEmail.isNotBlank())
            ) {
                if (id.isNotBlank() && name.isNotBlank() && userEmail.isNotBlank()) {
                    if (pattern.matcher(userEmail).find()) {
                        registerEmailViewModel.requestEmailCode(
                            email = userEmail,
                            type = EmailType.PASSWORD,
                        )
                    } else {
                        isEmailError = true
                    }
                } else {
                    changePasswordViewModel.checkId()
                    focusManager.clearFocus()
                }
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: ChangePasswordViewModel.Event,
): String = when (event) {
    is ChangePasswordViewModel.Event.BadRequestException -> {
        context.getString(R.string.BadRequest)
    }
    is ChangePasswordViewModel.Event.TooManyRequestException -> {
        context.getString(R.string.TooManyRequest)
    }
    is ChangePasswordViewModel.Event.ServerException -> {
        context.getString(R.string.ServerException)
    }
    else -> {
        context.getString(R.string.UnKnownException)
    }
}

private fun getStringFromEmailEvent(
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
}