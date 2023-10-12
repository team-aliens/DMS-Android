package team.aliens.dms_android.feature.resetpassword

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms_android.design_system.button.DormButtonColor
import team.aliens.dms_android.design_system.button.DormContainedLargeButton
import team.aliens.dms_android.design_system.color.DormColor
import team.aliens.dms_android.design_system.dialog.DormCustomDialog
import team.aliens.dms_android.design_system.dialog.DormDoubleButtonDialog
import team.aliens.dms_android.design_system.dialog.DormSingleButtonDialog
import team.aliens.dms_android.design_system.extension.RatioSpace
import team.aliens.dms_android.design_system.extension.Space
import team.aliens.dms_android.design_system.modifier.dormClickable
import team.aliens.dms_android.design_system.textfield.DormTextField
import team.aliens.dms_android.design_system.theme.DormTheme
import team.aliens.dms_android.design_system.toast.rememberToast
import team.aliens.dms_android.design_system.typography.Body2
import team.aliens.dms_android.design_system.typography.Body4
import team.aliens.dms_android.design_system.typography.Caption
import team.aliens.dms_android.feature.R
import team.aliens.dms_android.feature._legacy.AppLogo
import team.aliens.dms_android.feature._legacy.util.TopBar
import team.aliens.dms_android.feature.resetpassword.navigation.ResetPasswordNavigator
import java.util.regex.Pattern

@Suppress("ConstPropertyName")
private const val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"

@Destination
@Composable
fun ResetPasswordSetPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: ResetPasswordNavigator,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val toast = rememberToast()

    val focusManager = LocalFocusManager.current

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var isPasswordFormatError by remember { mutableStateOf(false) }
    var isPasswordMatchError by remember { mutableStateOf(false) }

    var isShowDialog by remember { mutableStateOf(false) }
    var isPressedBackButton by remember { mutableStateOf(false) }

    val onPasswordChange = { passwordValue: String ->
        if (passwordValue.length != password.length) isPasswordFormatError = false
        password = passwordValue
    }

    val onRepeatPasswordChange = { repeatPasswordValue: String ->
        if (repeatPasswordValue.length != repeatPassword.length) isPasswordMatchError = false
        repeatPassword = repeatPasswordValue
    }

    BackHandler(enabled = true) {
        isPressedBackButton = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DormTheme.colors.surface,
            )
            .dormClickable(
                rippleEnabled = false,
            ) {
                focusManager.clearFocus()
            },
    ) {

        TopBar(
            title = stringResource(R.string.ChangePassword),
            onPrevious = navigator::popBackStack,
        )

        if (isShowDialog) {
            DormCustomDialog(
                onDismissRequest = {},
            ) {
                DormSingleButtonDialog(
                    content = stringResource(id = R.string.SuccessChangePassword),
                    mainBtnText = stringResource(id = R.string.GoLogin),
                    onMainBtnClick = navigator::openSignIn,
                    mainBtnTextColor = DormColor.DormPrimary,
                )
            }

            Image(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 7.dp)
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(team.aliens.design_system.R.drawable.ic_information),
                contentDescription = stringResource(id = R.string.MainLogo),
            )

            Space(space = 1.dp)

            Body4(
                text = stringResource(id = R.string.SetNewPassword),
            )
        }

        if (isPressedBackButton) {
            DormCustomDialog(
                onDismissRequest = { /*TODO*/ },
            ) {
                DormDoubleButtonDialog(
                    content = stringResource(id = R.string.FinishResetPassword),
                    mainBtnText = stringResource(id = R.string.Yes),
                    subBtnText = stringResource(id = R.string.No),
                    onMainBtnClick = navigator::openSignIn,
                    onSubBtnClick = { isPressedBackButton = false },
                )
            }
        }

        LaunchedEffect(Unit) {
            changePasswordViewModel.editPasswordEffect.collect {
                when (it) {
                    is ChangePasswordViewModel.Event.ResetPasswordSuccess -> {
                        toast(context.getString(R.string.SuccessResetPassword))
                        navigator.openSignIn()
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
                    top = 38.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
        ) {
            AppLogo()
            Space(space = 8.dp)
            Body2(
                text = stringResource(id = R.string.ChangePassword),
            )
            Space(space = 4.dp)
            Caption(
                text = stringResource(id = R.string.PasswordWarning),
                color = DormColor.Gray500,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
            ) {
                Box(modifier = Modifier.height(76.dp)) {
                    DormTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        error = isPasswordFormatError,
                        isPassword = true,
                        hint = stringResource(id = R.string.ScanNewPassword),
                        errorDescription = stringResource(id = R.string.CheckPasswordFormat),
                        imeAction = ImeAction.Next,
                    )
                }
                Space(space = 7.dp)
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
                        keyboardActions = KeyboardActions {
                            focusManager.clearFocus()
                        },
                        imeAction = ImeAction.Done,
                    )
                }
            }
            RatioSpace(height = 0.742f)
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
                    /* todo
                        navController.previousBackStackEntry?.arguments?.run {
                        changePasswordViewModel.resetPassword(
                            accountId = getString("accountId").toString(),
                            emailVerificationCode = getString("authCode").toString(),
                            email = getString("email").toString(),
                            studentName = getString("name").toString(),
                            newPassword = password,
                        )
                    }*/
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