package team.aliens.dms_android.feature.auth.changepassword

import android.content.Context
import androidx.activity.compose.BackHandler
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
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.dialog.DormDoubleButtonDialog
import team.aliens.design_system.dialog.DormSingleButtonDialog
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.viewmodel.changepw.ChangePasswordViewModel
import team.aliens.presentation.R
import java.util.regex.Pattern

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val toast = rememberToast()

    val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var isPasswordFormatError by remember { mutableStateOf(false) }
    var isPasswordMatchError by remember { mutableStateOf(false) }

    var isShowDialog by remember { mutableStateOf(false) }
    var isPressedBackButton by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        isPressedBackButton = true
    }

    if (isShowDialog) {
        DormCustomDialog(
            onDismissRequest = {},
        ) {
            DormSingleButtonDialog(
                content = stringResource(id = R.string.SuccessChangePassword),
                mainBtnText = stringResource(id = R.string.GoLogin),
                onMainBtnClick = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Login) {
                            inclusive = true
                        }
                    }
                },
                mainBtnTextColor = DormColor.DormPrimary,
            )
        }
    }

    if (isPressedBackButton) {
        DormCustomDialog(
            onDismissRequest = { /*TODO*/ },
        ) {
            DormDoubleButtonDialog(
                content = stringResource(id = R.string.FinishResetPassword),
                mainBtnText = stringResource(id = R.string.Yes),
                subBtnText = stringResource(id = R.string.No),
                onMainBtnClick = {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Login) {
                            inclusive = true
                        }
                    }
                },
                onSubBtnClick = { isPressedBackButton = false },
            )
        }
    }

    val onPasswordChange = { passwordValue: String ->
        if (passwordValue.length != password.length) isPasswordFormatError = false
        password = passwordValue
        changePasswordViewModel.setNewPassword(password)
    }

    val onRepeatPasswordChange = { repeatPasswordValue: String ->
        if (repeatPasswordValue.length != repeatPassword.length) isPasswordMatchError = false
        repeatPassword = repeatPasswordValue
    }

    LaunchedEffect(Unit) {
        changePasswordViewModel.editPasswordEffect.collect {
            when (it) {
                is ChangePasswordViewModel.Event.ResetPasswordSuccess -> {
                    toast(context.getString(R.string.SuccessResetPassword))
                    navController.navigate(NavigationRoute.Login){
                        popUpTo(NavigationRoute.Login){
                            inclusive = true
                        }
                    }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 108.dp,
                start = 16.dp,
                end = 16.dp,
            )
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(8.dp))
        Body2(
            text = stringResource(id = R.string.ChangePassword),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Caption(
            text = stringResource(id = R.string.PasswordWarning),
            color = DormColor.Gray500,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
        ) {
            Box(
                modifier = Modifier.height(76.dp)
            ) {
                DormTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    error = isPasswordFormatError,
                    isPassword = true,
                    hint = stringResource(id = R.string.ScanNewPassword),
                    errorDescription = stringResource(id = R.string.NotCorrectPasswordFormat),
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Box(
                modifier = Modifier.height(76.dp),
            ) {
                DormTextField(
                    value = repeatPassword,
                    onValueChange = onRepeatPasswordChange,
                    error = isPasswordMatchError,
                    isPassword = true,
                    hint = stringResource(id = R.string.CheckScanNewPassword),
                    errorDescription = stringResource(id = R.string.MismatchRepeatPassword),
                )
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.742f))
        DormContainedLargeButton(
            text = stringResource(id = R.string.Check),
            color = DormButtonColor.Blue,
            enabled = (password.isNotBlank() && repeatPassword.isNotBlank())
        ) {
            if (password != repeatPassword) {
                isPasswordMatchError = true
            } else if (!Pattern.compile(passwordFormat).matcher(password).find()) {
                isPasswordFormatError = true
            } else {
                changePasswordViewModel.resetPassword()
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: ChangePasswordViewModel.Event,
): String = when(event){
    is ChangePasswordViewModel.Event.BadRequestException -> {
        context.getString(R.string.BadRequest)
    }
    is ChangePasswordViewModel.Event.UnauthorizedException -> {
        context.getString(R.string.NoSameCode)
    }
    is ChangePasswordViewModel.Event.NotFoundException -> {
        context.getString(R.string.ChangePasswordNotFound)
    }
    is ChangePasswordViewModel.Event.TooManyRequestException -> {
        context.getString(R.string.TooManyRequest)
    }
    is ChangePasswordViewModel.Event.ServerException -> {
        context.getString(R.string.ServerException)
    }
    else -> context.getString(R.string.UnKnownException)
}
