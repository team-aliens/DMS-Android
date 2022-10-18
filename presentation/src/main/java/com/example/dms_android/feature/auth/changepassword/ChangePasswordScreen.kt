package com.example.dms_android.feature.auth.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Composable
fun ChangePasswordScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray100),
    ) {
        MainValue()
        PasswordTextField()
        ScanNewPasswordButton()
    }
}

@Composable
fun MainValue() {
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
                        top = 16.dp,
                        bottom = 12.dp
                    )
                    .height(24.dp)
                    .width(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "backButton",
            )
            Image(
                modifier = Modifier
                    .padding(
                        top = 32.dp,
                        bottom = 7.dp
                    )
                    .height(85.dp)
                    .width(85.dp),
                painter = painterResource(id = R.drawable.ic_information_toast),
                contentDescription = "MainLogo",
            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
            )
            Body4(
                text = "새 비밀번호 설정",
                color = DormColor.Gray600,
            )
        }
    }
}

@Composable
fun PasswordTextField() {

    var passwordValue by remember { mutableStateOf("") }
    var passwordCheckValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 90.dp,
                end = 16.dp
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
        Spacer(
            modifier = Modifier
                .height(37.dp)
        )
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
fun ScanNewPasswordButton() {
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
            TODO("ViewModel Function")
        }
    }
}

@Preview
@Composable
fun ChangePasswordScreenPreView() {
    ChangePasswordScreen()
}
