package com.example.dms_android.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.dms_android.R
import com.example.design_system.color.DormColor
import com.example.design_system.textfield.DormTextField
import com.example.design_system.button.DormTextCheckBox
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body6
import com.example.design_system.typography.Title5

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray100),
    ) {
        MainTitle()
        TextField()
        AutoLogin()
        AddFunction()
        LoginButton()
    }
}

@Composable
fun MainTitle() {
    Box(
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        top = 56.dp,
                        bottom = 7.dp
                    )
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = "MainLogo",
            )
            Title5(text = "DMS For Android")
            Spacer(
                modifier = Modifier
                    .height(1.dp)
            )
            Body4(text = "더 편한 기숙사 생활을 위해")
        }
    }
}

@Composable
fun TextField() {

    var idValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 6.dp,
                    end = 16.dp
                ),
        ) {
            Spacer(
                modifier = Modifier
                    .height(52.dp),
            )
            DormTextField(
                value = idValue,
                onValueChange = { idValue = it },
                hint = stringResource(id = R.string.Login),
            )
            Spacer(
                modifier = Modifier
                    .height(36.dp),
            )
            DormTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                isPassword = true,
                hint = stringResource(id = R.string.Password),
            )
        }
    }
}

@Composable
fun AutoLogin() {

    var checked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 22.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .height(25.dp),
            )
            DormTextCheckBox(
                text = stringResource(id = R.string.AutoLogin),
                checked = checked,
                onCheckedChange = {
                    checked = !checked
                },
            )
        }
    }
}

@Composable
fun AddFunction() {
    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally)
                .padding(
                    start = 10.dp,
                    top = 24.dp,
                    end = 10.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Body6(text = "회원가입하기")
            Body6(text = "|")
            Body6(text = "아이디 찾기")
            Body6(text = "|")
            Body6(text = "비밀번호 변경")
        }
    }
}

@Composable
fun LoginButton() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 60.dp
            )
            .fillMaxSize(),
    ) {
        DormContainedLargeButton(
            text = stringResource(id = R.string.Login),
            color = DormButtonColor.Blue,
        ) {
            TODO("ViewModel Function")
        }
    }
}


@Preview
@Composable
fun LoginPreView() {
    LoginScreen()
}
