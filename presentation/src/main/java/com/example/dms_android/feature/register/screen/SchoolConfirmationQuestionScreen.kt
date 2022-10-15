package com.example.dms_android.feature.register.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.textfield.DormTextField
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body6
import com.example.design_system.typography.ButtonText

@Preview
@Composable
fun SchoolConfirmationQuestionScreen() {
    var value by remember { mutableStateOf(String()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 77.dp)
        ) {
            Image(
                painter = painterResource(id = DormIcon.Applicate.drawableId),
                contentDescription = null,
                modifier = Modifier.size(49.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Body4(text = "학교 확인 질문", color = DormColor.Gray600)
            Spacer(modifier = Modifier.height(60.dp))
            Body4(text = "우리 학교 학생 수는?", color = DormColor.Gray700)
            Spacer(modifier = Modifier.height(8.dp))
            DormTextField(
                value = value,
                onValueChange = { value = it },
                error = "학교 인증 질문과 답변이 일치하지 않습니다!",
                hint = "답변",
            )

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp)
            ) {
                Column() {

                    DormContainedLargeButton(
                        text = "인증",
                        color = DormButtonColor.Blue,
                        enabled = false
                    ) {
                    }
                    Spacer(modifier = Modifier.height(17.dp))
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 88.dp)) {
                        Body6(text = "이미 계정이 있으신가요?", color = DormColor.Gray500)
                        Spacer(modifier = Modifier.width(8.dp))
                        ButtonText(text = "로그인", color = DormColor.Gray600)
                    }
                }
            }
        }


    }
}