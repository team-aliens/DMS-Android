package team.aliens.dms_android.feature.mypage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import team.aliens.design_system.typography.OverLine
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.changepw.ChangePasswordViewModel
import team.aliens.presentation.R
import java.util.regex.Pattern

@Composable
fun MyPageChangePasswordScreen(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
    ) {

        val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"

        val focusManager = LocalFocusManager.current

        val toast = rememberToast()

        val context = LocalContext.current

        var newPassword by remember { mutableStateOf("") }
        var repeatPassword by remember { mutableStateOf("") }

        val isValueEmpty = (newPassword.isNotBlank() && repeatPassword.isNotBlank())

        var isPasswordMatchError by remember { mutableStateOf(false) }
        var isPasswordFormatError by remember { mutableStateOf(false) }

        val onNewPasswordChange = { value: String ->
            if (value.length != newPassword.length) isPasswordFormatError = false
            newPassword = value
            changePasswordViewModel.setNewPassword(newPassword)
        }

        val onRepeatPasswordChange = { value: String ->
            if (value.length != repeatPassword.length) isPasswordMatchError = false
            repeatPassword = value
            changePasswordViewModel.setRepeatPassword(repeatPassword)
        }

        LaunchedEffect(Unit) {
            changePasswordViewModel.setCurrentPassword(
                navController.previousBackStackEntry?.arguments?.getString("currentPassword")
                    .toString()
            )
            changePasswordViewModel.editPasswordEffect.collect {
                when (it) {
                    is ChangePasswordViewModel.Event.EditPasswordSuccess -> {
                        navController.popBackStack()
                        navController.popBackStack()
                        toast(context.getString(R.string.SuccessChangePassword))
                    }
                    else -> {
                        toast(
                            getStringFromEvent(
                                context = context,
                                event = it,
                            ),
                        )
                    }
                }
            }
        }

        TopBar(title = stringResource(id = R.string.ChangePassword)) {
            focusManager.clearFocus()
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DormColor.Gray100)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(
                modifier = Modifier.height(
                    46.dp,
                )
            )
            Image(
                modifier = Modifier
                    .size(
                        width = 96.dp,
                        height = 34.dp,
                    ),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Body2(
                text = stringResource(R.string.ChangePassword),
                color = DormColor.Gray600,
            )
            Spacer(modifier = Modifier.height(6.dp))
            OverLine(
                text = stringResource(id = R.string.PwWarning),
                color = DormColor.Gray500,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.835f)
                    .padding(top = 60.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(0.2f)
                ) {
                    DormTextField(
                        value = newPassword,
                        onValueChange = onNewPasswordChange,
                        hint = stringResource(R.string.EnterPassword),
                        isPassword = true,
                        error = isPasswordFormatError,
                        errorDescription = stringResource(id = R.string.NotCorrectPasswordFormat)
                    )
                }

                DormTextField(
                    value = repeatPassword,
                    onValueChange = onRepeatPasswordChange,
                    hint = stringResource(R.string.ReEnterPassword),
                    isPassword = true,
                    error = isPasswordMatchError,
                    errorDescription = stringResource(id = R.string.MismatchRepeatPassword),
                )
            }
            DormContainedLargeButton(
                text = stringResource(R.string.Check),
                color = DormButtonColor.Blue,
                enabled = isValueEmpty,
            ) {
                if (newPassword != repeatPassword) {
                    isPasswordMatchError = true
                } else if (!Pattern.compile(passwordFormat).matcher(newPassword).find()) {
                    isPasswordFormatError = true
                } else {
                    changePasswordViewModel.editPassword()
                }
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: ChangePasswordViewModel.Event,
): String =
    context.getString(
        when (event) {
            is ChangePasswordViewModel.Event.BadRequestException -> R.string.PwWarning
            is ChangePasswordViewModel.Event.UnauthorizedException -> R.string.CheckPassword
            is ChangePasswordViewModel.Event.ForbiddenException -> R.string.Forbidden
            is ChangePasswordViewModel.Event.TooManyRequestException -> R.string.TooManyRequest
            is ChangePasswordViewModel.Event.ServerException -> R.string.ServerException
            else -> R.string.UnKnownException
        }
    )
