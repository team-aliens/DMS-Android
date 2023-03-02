package team.aliens.dms_android.feature.register.ui.password

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.typography.Body2
import team.aliens.design_system.typography.Body3
import team.aliens.dms_android.component.AppLogo
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.presentation.R

@Composable
fun SignUpPasswordScreen(
    navController: NavController,
) {

    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    val onPasswordChange = { value: String ->
        password = value
    }

    val onPasswordRepeatChange = { value: String ->
        passwordRepeat = value
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
            text = stringResource(id = R.string.SetId)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.829f)
                .padding(top = 60.dp)
        ) {
            DormTextField(
                value = password,
                onValueChange = onPasswordChange,
                hint = stringResource(id = R.string.EnterPassword),
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(20.dp))
            DormTextField(
                value = passwordRepeat,
                onValueChange = onPasswordRepeatChange,
                hint = stringResource(id = R.string.ReEnterPassword),
                isPassword = true,
                error = isError,
                errorDescription = stringResource(id = R.string.CheckPassword),
            )
        }
        DormContainedLargeButton(
            text = stringResource(id = R.string.Next),
            color = DormButtonColor.Blue,
            enabled = password.isNotEmpty() && passwordRepeat.isNotEmpty()
        ) {
            if (password != passwordRepeat) {
                isError = true
            }else {
                navController.currentBackStackEntry?.arguments?.run {
                    putString("schoolCode", navController.previousBackStackEntry?.arguments?.getString("schoolCode"))
                    putString("schoolAnswer", navController.previousBackStackEntry?.arguments?.getString("schoolAnswer"))
                    putString("email", navController.previousBackStackEntry?.arguments?.getString("email"))
                    putString("authCode", navController.previousBackStackEntry?.arguments?.getString("authCode"))
                    putInt("classRoom", navController.previousBackStackEntry?.arguments?.getInt("classRoom") ?: 0)
                    putInt("grade", navController.previousBackStackEntry?.arguments?.getInt("grade") ?: 0)
                    putInt("number", navController.previousBackStackEntry?.arguments?.getInt("number") ?: 0)
                    putString("accountId", navController.previousBackStackEntry?.arguments?.getString("accountId"))
                    putString("password", password)
                    putString("profileImageUrl", "https://image-dms.s3.ap-northeast-2.amazonaws.com/59fd0067-93ef-4bcb-8722-5bc8786c5156%7C%7C%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.png")

                }
                navController.navigate(NavigationRoute.SignUpPolicy)
            }
        }
    }
}