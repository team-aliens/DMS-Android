package com.example.dms_android.feature.register.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body5
import com.example.design_system.typography.ButtonText
import com.example.dms_android.R
import com.example.dms_android.feature.register.screen.component.OTP_VIEW_TYPE_UNDERLINE
import com.example.dms_android.feature.register.screen.component.OtpView

@Preview
@Composable
fun SchoolCertificationCodeScreen() {
    var otpValue by remember { mutableStateOf("") }
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
                modifier = Modifier.size(49.dp),
            )
            Spacer(
                modifier = Modifier
                    .height(7.dp)
            )
            Body4(text = stringResource(R.string.SchoolVerificationCode), color = DormColor.Gray600)
            Spacer(
                modifier = Modifier
                    .height(100.dp)
            )
            OtpView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                otpText = otpValue,
                onOtpTextChange = {
                    otpValue = it
                },
                type = OTP_VIEW_TYPE_UNDERLINE,
                password = true,
                containerSize = 24.dp,
                passwordChar = "⚫",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                charColor = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Body5(
                    text = stringResource(R.string.EmailEightCode),
                    color = DormColor.Gray500,
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Body5(
                    text = "3 : 00",
                    color = DormColor.DormPrimary,
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ButtonText(
                        text = stringResource(R.string.ResendVerificationCode),
                        color = DormColor.Gray600
                    )
                    Spacer(
                        modifier = Modifier
                            .height(29.dp)
                    )
                    DormContainedLargeButton(
                        text = stringResource(R.string.verification),
                        color = DormButtonColor.Blue,
                        enabled = false,
                    ) {
                    }
                }
            }
        }
    }
}

