package team.aliens.dms_android.feature.signup.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.regex.Pattern
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.presentation.R

@Composable
fun SignUpSetPasswordScreen(
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current

    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }

    val passwordFormat = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,20}"

    var isPasswordMatchError by remember { mutableStateOf(false) }
    var isPasswordFormatError by remember { mutableStateOf(false) }

    val onPasswordChange = { value: String ->
        if (value.length != password.length) isPasswordFormatError = false
        password = value
    }

    val onPasswordRepeatChange = { value: String ->
        if (value.length != passwordRepeat.length) isPasswordMatchError = false
        passwordRepeat = value
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
        AppLogo(
            darkIcon = isSystemInDarkTheme(),
        )
        Space(space = 8.dp)
        Body2(text = stringResource(id = R.string.SetPassword))
        Space(space = 4.dp)
        Caption(
            text = stringResource(id = R.string.PasswordWarning),
            color = DormTheme.colors.primaryVariant,
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.824f)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxHeight(0.17f),
            ) {
                DormTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(id = R.string.EnterPassword),
                    isPassword = true,
                    error = isPasswordFormatError,
                    errorDescription = stringResource(id = R.string.CheckPasswordFormat),
                    imeAction = ImeAction.Next,
                )
            }
            DormTextField(
                value = passwordRepeat,
                onValueChange = onPasswordRepeatChange,
                hint = stringResource(id = R.string.ReEnterPassword),
                isPassword = true,
                error = isPasswordMatchError,
                errorDescription = stringResource(id = R.string.CheckPassword),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                imeAction = ImeAction.Done,
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.Next),
            color = DormButtonColor.Blue,
            enabled = password.isNotEmpty() && passwordRepeat.isNotEmpty()
        ) {
            if (password != passwordRepeat) {
                isPasswordMatchError = true
            } else if (!Pattern.compile(passwordFormat).matcher(password).find()) {
                isPasswordFormatError = true
            } else {

                navController.currentBackStackEntry?.arguments?.run {
                    putString(
                        "schoolCode",
                        navController.previousBackStackEntry?.arguments?.getString("schoolCode")
                    )
                    putString(
                        "schoolAnswer",
                        navController.previousBackStackEntry?.arguments?.getString("schoolAnswer")
                    )
                    putString(
                        "email",
                        navController.previousBackStackEntry?.arguments?.getString("email")
                    )
                    putString(
                        "authCode",
                        navController.previousBackStackEntry?.arguments?.getString("authCode")
                    )
                    putInt(
                        "classRoom",
                        navController.previousBackStackEntry?.arguments?.getInt("classRoom") ?: 0
                    )
                    putInt(
                        "grade",
                        navController.previousBackStackEntry?.arguments?.getInt("grade") ?: 0
                    )
                    putInt(
                        "number",
                        navController.previousBackStackEntry?.arguments?.getInt("number") ?: 0
                    )
                    putString(
                        "accountId",
                        navController.previousBackStackEntry?.arguments?.getString("accountId")
                    )
                    putString("password", password)
                }
                navController.navigate(DmsRoute.SignUp.SetProfileImage)
            }
        }
    }
}