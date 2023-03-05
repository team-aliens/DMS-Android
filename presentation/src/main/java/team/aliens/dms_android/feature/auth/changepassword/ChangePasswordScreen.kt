package team.aliens.dms_android.feature.auth.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.changepw.ChangePasswordViewModel
import team.aliens.presentation.R

@Composable
fun ChangePasswordScreen(
    navController: NavHostController,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DormTheme.colors.background,
            ),
    ) {

        TopBar(
            title = stringResource(R.string.ChangePassword),
        ) {
            navController.popBackStack()
        }

        MainValue()
        PasswordTextField()
        ScanNewPasswordButton(changePasswordViewModel)
    }
}

@Composable
fun MainValue() {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Image(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 7.dp)
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = stringResource(id = R.string.MainLogo),
            )

            Spacer(
                modifier = Modifier.height(1.dp),
            )

            Body4(
                text = stringResource(id = R.string.SetNewPassword),
            )
        }
    }
}

@Composable
fun PasswordTextField() {

    var passwordValue by remember { mutableStateOf("") }
    var passwordCheckValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 90.dp,
            end = 16.dp,
        ),
    ) {
        DormTextField(
            value = passwordValue,
            onValueChange = {
                passwordValue = it
            },
            isPassword = true,
            hint = stringResource(id = R.string.ScanNewPassword),
        )
        Spacer(modifier = Modifier.height(37.dp))
        DormTextField(
            value = passwordCheckValue,
            onValueChange = {
                passwordCheckValue = it
            },
            isPassword = true,
            hint = stringResource(id = R.string.CheckScanNewPassword),
        )
    }
}

@Composable
fun ScanNewPasswordButton(
    changePasswordViewModel: ChangePasswordViewModel,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 60.dp,
            )
            .fillMaxSize(),
    ) {
        DormContainedLargeButton(
            text = stringResource(id = R.string.Check),
            color = DormButtonColor.Blue,
        ) {
            // todo
        }
    }
}
