package team.aliens.dms_android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.textfield.DormTextField
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.OverLine
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Composable
fun MyPageChangePasswordScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DormColor.Gray200)) {
        TopBar(title = stringResource(id = R.string.ChangePassword))
        ChangePwValue()
    }
}

@Composable
fun ChangePwValue() {

    var passwordValue by remember { mutableStateOf("") }
    var rePasswordValue by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(DormColor.Gray100)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 47.dp)) {
            Image(
                painter = painterResource(id = R.drawable.temporarylogo),
                contentDescription = null,
                modifier = Modifier.size(49.dp),
            )
            Spacer(modifier = Modifier.height(7.dp))
            Body4(text = stringResource(R.string.ChangePassword), color = DormColor.Gray600)
            Spacer(modifier = Modifier.height(6.dp))
            OverLine(
                text = stringResource(id = R.string.PwWarning),
                color = DormColor.Gray500,
            )
            Spacer(modifier = Modifier.height(60.dp))
            DormTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                hint = stringResource(R.string.EnterPassword),
                isPassword = true,
            )
            Spacer(modifier = Modifier.height(37.dp))
            DormTextField(
                value = rePasswordValue,
                onValueChange = { rePasswordValue = it },
                hint = stringResource(R.string.ReEnterPassword),
                error = stringResource(R.string.CheckPassword),
                isPassword = true,
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                DormContainedLargeButton(
                    text = stringResource(R.string.Next),
                    color = DormButtonColor.Blue,
                    enabled = false,
                ) {}
            }
        }
    }
}

@Preview
@Composable
fun ChangePwPreView() {
    MyPageChangePasswordScreen()
}
