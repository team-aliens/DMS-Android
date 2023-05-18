package team.aliens.dms_android.feature.auth.changepassword

import android.content.Context
import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import team.aliens.design_system.extension.RatioSpace
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.DmsRoute
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.feature.register.ui.email.RegisterEmailViewModel
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.presentation.R

@Composable
fun UserVerification(
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
    }

    val onNameChange = { userName: String ->
        if (userName.length != name.length) isNameError = false
        name = userName
    }

    val onEmailChange = { value: String ->
        if (value.length != userEmail.length) isEmailError = false
        userEmail = value
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
                    navController.currentBackStackEntry?.arguments?.run {
                        putString("accountId", id.trim())
                        putString("name", name.trim())
                        putString("email", userEmail.trim())
                    }
                    navController.navigate(DmsRoute.Auth.ResetPassword)
                }
                is RegisterEmailEvent.TooManyRequestsException -> {
                    toast(context.getString(R.string.Retry))
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
            .background(
                DormTheme.colors.surface,
            )
            .fillMaxHeight(0.8f)
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            }
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
                    keyboardActions = KeyboardActions {
                        if (id.isNotBlank()) {
                            changePasswordViewModel.checkId(
                                accountId = id,
                            )
                            focusManager.clearFocus()
                        }
                    },
                    imeAction = ImeAction.Done,
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
                                DormTheme.colors.background,
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
                            color = DormTheme.colors.primary,
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
                            hint = stringResource(id = R.string.EnterName),
                            imeAction = ImeAction.Next,
                        )
                    }
                    Space(space = 32.dp)
                    Box(
                        modifier = Modifier
                            .height(68.dp)
                    ) {
                        DormTextField(
                            value = userEmail,
                            onValueChange = onEmailChange,
                            hint = stringResource(id = R.string.EnterEmailAddress),
                            error = isEmailError,
                            keyboardType = KeyboardType.Email,
                            errorDescription = context.getString(R.string.NotValidEmailFormat),
                            keyboardActions = KeyboardActions {
                                focusManager.clearFocus()
                            },
                            imeAction = ImeAction.Done,
                        )
                    }
                }
            }

            RatioSpace(height = if (emailResponse.isEmpty()) 0.05f else 0.622f)

            DormContainedLargeButton(
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
                enabled = if (emailResponse.isEmpty()) id.isNotBlank() && !isIdError
                else (id.isNotBlank() && name.isNotBlank() && userEmail.isNotBlank())
            ) {
                if (id.isNotBlank() && name.isNotBlank() && userEmail.isNotBlank()) {
                    if (pattern.matcher(userEmail).find()) {
                        registerEmailViewModel.requestEmailCode(
                            email = userEmail.trim(),
                            type = EmailVerificationType.PASSWORD,
                        )
                    } else {
                        isEmailError = true
                    }
                } else {
                    changePasswordViewModel.checkId(
                        accountId = id.trim(),
                    )
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