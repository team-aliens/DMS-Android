package com.example.dms_android.feature.auth.selfconfirmation


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
import com.example.design_system.typography.Body5
import com.example.dms_android.R

@Preview
@Composable
fun SelfConfirmScreen() {
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
                text = stringResource(R.string.VerificationMine),
                color = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(60.dp)
            )
            IdTextField()
            CorrectIdView()
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
            ) {
                DormContainedLargeButton(
                    text = stringResource(R.string.Next),
                    color = DormButtonColor.Blue,
                    enabled = false,
                ) {
                }
            }
        }
    }
}

@Composable
fun IdTextField() {
    var idValue by remember { mutableStateOf(String()) }
    DormTextField(
        value = idValue,
        onValueChange = { idValue = it },
        error = stringResource(
            R.string.NotSameName
        ),
        hint = stringResource(R.string.enter_id),
    )
}

@Composable
fun CorrectIdView() {
    Spacer(
        modifier = Modifier
            .height(12.dp)
    )
    Column(
        modifier = Modifier
            .height(68.dp)
            .fillMaxWidth()
            .background(color = DormColor.Gray200),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Body5(text = stringResource(R.string.SameIdAndEmail))
            Spacer(modifier = Modifier.height(5.dp))
            //TODO:Id들어갈 자리입니다
            Body5(text = "id", color = DormColor.Darken200)
        }

    }

    Spacer(
        modifier = Modifier
            .height(24.dp)
    )
    var nameValue by remember { mutableStateOf(String()) }
    DormTextField(
        value = nameValue,
        onValueChange = { nameValue = it },
        hint = stringResource(R.string.EnterName),
    )
    Spacer(
        modifier = Modifier
            .height(37.dp)
    )
    var emailValue by remember { mutableStateOf(String()) }
    DormTextField(
        value = emailValue,
        onValueChange = { emailValue = it },
        hint = stringResource(R.string.enter_email_address),
    )
}

@Composable
fun VerificationOtpView() {

}