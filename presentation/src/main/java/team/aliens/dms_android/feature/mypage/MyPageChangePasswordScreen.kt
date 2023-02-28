package team.aliens.dms_android.feature.mypage

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

        val focusManager = LocalFocusManager.current

        val toast = rememberToast()

        val context = LocalContext.current

        var newPassword by remember { mutableStateOf("") }
        var repeatPassword by remember { mutableStateOf("") }

        val isValueEmpty = (newPassword.isNotBlank() && repeatPassword.isNotBlank())

        val onNewPasswordChange = { value: String ->
            newPassword = value
            changePasswordViewModel.setNewPassword(newPassword)
        }

        val onRepeatPasswordChange = { value: String ->
            repeatPassword = value
            changePasswordViewModel.setRepeatPassword(repeatPassword)
        }

        LaunchedEffect(Unit) {
            changePasswordViewModel.editPasswordEffect.collect {
                when (it) {
                    is ChangePasswordViewModel.Event.EditPasswordSuccess -> {
                        // TODO navigate
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
            Body2(text = stringResource(R.string.ChangePassword), color = DormColor.Gray600)
            Spacer(modifier = Modifier.height(6.dp))
            OverLine(
                text = stringResource(id = R.string.PwWarning),
                color = DormColor.Gray500,
            )
            Spacer(modifier = Modifier.height(60.dp))
            DormTextField(
                value = newPassword,
                onValueChange = onNewPasswordChange,
                hint = stringResource(R.string.EnterPassword),
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(36.dp))
            DormTextField(
                value = repeatPassword,
                onValueChange = onRepeatPasswordChange,
                hint = stringResource(R.string.ReEnterPassword),
                isPassword = true,
                error = (isValueEmpty && newPassword != repeatPassword),
                errorDescription = stringResource(id = R.string.MismatchRepeatPassword),
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                DormContainedLargeButton(
                    text = stringResource(R.string.Check),
                    color = DormButtonColor.Blue,
                    enabled = isValueEmpty,
                ) {
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
