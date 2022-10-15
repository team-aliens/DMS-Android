package com.example.design_system.toast

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R

@Composable
fun ToastView(
    messageTxt: String,
    stateToast: Int,
    textColor: Color,
) {
    var painter = painterResource(id = R.drawable.ic_information_toast)

    when(stateToast) {
        1 -> painter = painterResource(id = R.drawable.ic_information_toast)
        2 -> painter = painterResource(id = R.drawable.ic_error_toast)
        3 -> painter = painterResource(id = R.drawable.ic_success_toast)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 117.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            color = Color.Transparent,
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = Color.White,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(24.dp)
                        .width(24.dp),
                    painter = painter,
                    contentDescription = "",
                )
                Text(
                    text = messageTxt,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    style = TextStyle(color = textColor),
                )
            }
        }
    }
}

@Composable
fun MakeToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
    stateToast: Int,
    textColor: Color,
) {
    val toast = ToastMaker(LocalContext.current)
    toast.MakeTest(
        message = message,
        duration = duration,
        stateToast = stateToast,
        textColor = textColor,
    )
    toast.show()
}

@Preview
@Composable
fun ToastExample() {
    MakeToast(
        message = "회원가입 성공!",
        stateToast = 3,
        textColor = Color.Blue,
    )
}
