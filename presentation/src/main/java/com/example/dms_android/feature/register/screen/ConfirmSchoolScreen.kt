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
import com.example.design_system.typography.Body6
import com.example.design_system.typography.ButtonText
import com.example.dms_android.R

@Preview
@Composable
fun ConfirmSchoolScreen() {
    var replyValue by remember { mutableStateOf("") }
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
                modifier = Modifier.height(7.dp)
            )
            Body4(
                text = stringResource(R.string.question_confirm_school),
                color = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier.height(60.dp)
            )
            Body4(text = stringResource(R.string.school_students), color = DormColor.Gray700)
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            DormTextField(
                value = replyValue,
                onValueChange = { replyValue = it },
                error = stringResource(R.string.inconsistent_school_reply),
                hint = stringResource(R.string.reply),
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
            ) {
                Column() {

                    DormContainedLargeButton(
                        text = stringResource(R.string.verification),
                        color = DormButtonColor.Blue,
                        enabled = false,
                    ) {
                    }
                    Spacer(
                        modifier = Modifier.height(17.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 88.dp)
                    ) {
                        Body6(
                            text = stringResource(R.string.already_account),
                            color = DormColor.Gray500
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        ButtonText(text = stringResource(R.string.login), color = DormColor.Gray600)
                    }
                }
            }
        }
    }
}