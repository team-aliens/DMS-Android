package com.example.dms_android.feature.register.screen.email

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.design_system.icon.DormIcon
import com.example.design_system.textfield.DormTextField
import com.example.design_system.typography.Body4
import com.example.dms_android.R

@Preview
@Composable
fun EnterEmailScreen() {
    var value by remember { mutableStateOf(String()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray100)
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
                modifier = Modifier
                    .size(49.dp),
            )
            Spacer(
                modifier = Modifier
                    .height(7.dp)
            )
            Body4(
                text = stringResource(R.string.enter_email_address),
                color = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(60.dp)
            )
            DormTextField(
                value = value,
                onValueChange = { value = it },
                error = stringResource(R.string.not_valid_email_format),
                hint = stringResource(R.string.enter_email_address),
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                DormContainedLargeButton(
                    text = stringResource(R.string.send_verification_code),
                    color = DormButtonColor.Blue,
                    enabled = false,
                ) {
                }
            }
        }
    }
}