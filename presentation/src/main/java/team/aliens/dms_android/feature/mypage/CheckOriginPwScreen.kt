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
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Composable
fun CheckOriginPwScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DormColor.Gray200)) {
        TopBar(title = stringResource(id = R.string.ChangePassword))
        CheckPwTitle()
        CheckPwValue()
    }
}

@Composable
fun CheckPwTitle() {
    Column(horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(start = 24.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(49.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Body4(text = stringResource(id = R.string.OriginPw))
    }
}

@Composable
fun CheckPwValue() {

    var passwordValue by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.height(110.dp))
    DormTextField(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
        value = passwordValue,
        onValueChange = { passwordValue = it },
        hint = stringResource(R.string.EnterPassword),
        isPassword = true,
    )

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 60.dp),
    ) {
        DormContainedLargeButton(
            text = stringResource(R.string.Next),
            color = DormButtonColor.Blue,
            enabled = false,
        ) {

        }
    }
}

@Preview
@Composable
fun CheckPwPreView() {
    CheckOriginPwScreen()
}
