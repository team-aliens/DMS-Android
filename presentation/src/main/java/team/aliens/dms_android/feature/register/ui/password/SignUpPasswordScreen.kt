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
            Spacer(modifier = Modifier.height(36.dp))
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
                navController.navigate("")
            }
        }
    }
}