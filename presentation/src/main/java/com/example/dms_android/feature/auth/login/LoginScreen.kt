package com.example.dms_android.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.button.DormButtonColor
import com.example.dms_android.R
import com.example.design_system.color.DormColor
import com.example.design_system.textfield.DormTextField
import com.example.design_system.typography.NotoSansFamily
import com.example.design_system.button.DormTextCheckBox
import com.example.design_system.button.DormContainedLargeButton

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
        Column() {
            Image(
                modifier = Modifier
                    .padding(15.dp, 40.dp, 0.dp, 7.dp)
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = "",
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, start = 15.dp),
                text = "DMS For Android",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = DormColor.Gray900),
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, start = 15.dp),
                text = "더 편한 기숙사를 생활을 위해",
                fontSize = 16.sp,
                fontFamily = NotoSansFamily,
                fontWeight = FontWeight.Normal,
                style = TextStyle(color = DormColor.Gray600),
            )
        }
    }
}

@Composable
fun TextField() {

    var idValue by remember { mutableStateOf(String()) }
    var passwordValue by remember { mutableStateOf(String()) }

    Box(
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .height(52.dp),
            )
            DormTextField(
                value = idValue,
                onValueChange = { idValue = it },
                hint = "아이디",
            )
            Spacer(
                modifier = Modifier
                    .height(36.dp),
            )
            DormTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                isPassword = true,
                hint = "비밀번호",
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
                text = "자동로그인",
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
                .padding(10.dp, 24.dp, 10.dp, 0.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "회원가입하기",
                fontSize = 12.sp,
                color = DormColor.Gray500,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = "|",
                fontSize = 12.sp,
                color = DormColor.Gray500,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = "아이디 찾기",
                fontSize = 12.sp,
                color = DormColor.Gray500,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = "|",
                fontSize = 12.sp,
                color = DormColor.Gray500,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = "비밀번호 변경",
                fontSize = 12.sp,
                color = DormColor.Gray500,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Composable
fun LoginButton() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 60.dp)
            .fillMaxSize(),
    ) {
        DormContainedLargeButton(
            text = "로그인",
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