package com.example.dms_android.feature.register.screen

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
import androidx.compose.ui.graphics.Color
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
fun SetPasswordScreen() {
    var passwordValue by remember { mutableStateOf("") }
    var repasswordValue by remember { mutableStateOf("") }
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

            Spacer(modifier = Modifier.height(7.dp))

            Body4(text = stringResource(R.string.set_password), color = DormColor.Gray600)

            Spacer(modifier = Modifier.height(60.dp))

            DormTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                hint = stringResource(R.string.enter_password),
                isPassword = true,
            )

            Spacer(modifier = Modifier.height(37.dp))

            DormTextField(
                value = repasswordValue,
                onValueChange = { repasswordValue = it },
                hint = stringResource(R.string.reenter_password),
                error = stringResource(R.string.check_password),
                isPassword = true,
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                DormContainedLargeButton(
                    text = stringResource(R.string.next),
                    color = DormButtonColor.Blue,
                    enabled = false,
                ) {
                }
            }
        }
    }
}