package team.aliens.dms_android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.textfield.DormTextField
import com.example.design_system.typography.Body4
import com.example.dms_android.R
import com.example.dms_android.util.TopBar

@Composable
fun CheckOriginPwScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200)
    ) {
        TopBar(title = stringResource(id = R.string.ChangePassword))
        CheckPwTitle()
        CheckPwValue()
    }
}

@Composable
fun CheckPwTitle() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(start = 24.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(40.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.temporarylogo),
            contentDescription = null,
            modifier = Modifier.size(49.dp),
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Body4(text = stringResource(id = R.string.OriginPw))
    }
}

@Composable
fun CheckPwValue() {

    var passwordValue by remember { mutableStateOf("") }

    Spacer(
        modifier = Modifier
            .height(110.dp)
    )
    DormTextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp),
        value = passwordValue,
        onValueChange = { passwordValue = it },
        hint = stringResource(R.string.enter_password),
        isPassword = true,
    )

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 60.dp),
    ) {
        DormContainedLargeButton(
            text = stringResource(R.string.next),
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
