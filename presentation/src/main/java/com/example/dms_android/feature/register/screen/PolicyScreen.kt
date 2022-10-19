package com.example.dms_android.feature.register.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.button.DormTextCheckBox
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body4
import com.example.dms_android.R

@Preview
@Composable
fun PolicyScreen() {
    val context = LocalContext.current
    val webViewClient = WebViewClient()
    var confirmState by remember {
        mutableStateOf(false)
    }
    var checked by remember {
        mutableStateOf(false)
    }
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
            Body4(
                text = stringResource(R.string.CheckRegisterPolicy),
                color = DormColor.Gray600,
            )
            Spacer(
                modifier = Modifier
                    .height(35.dp)
            )
            AndroidView(
                modifier = Modifier.height(300.dp),
                factory = {
                    WebView(context).apply {
                        this.webViewClient = webViewClient
                        this.loadUrl("https://velog.io/@dev-junku/Android-Naver-Android-Jetpack-Compose-%EC%A0%81%EC%9A%A9-%ED%9B%84%EA%B8%B0-%EB%B0%9C%ED%91%9C-%EC%A0%95%EB%A6%AC")
                    }
                },
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .fillMaxSize(),
            ) {
                DormTextCheckBox(
                    checked = checked,
                    text = stringResource(R.string.CheckAllPolicy),
                    onCheckedChange = {
                        checked = !checked
                        confirmState = !confirmState
                    },
                )
                Spacer(modifier = Modifier.height(17.dp))
                DormContainedLargeButton(
                    text = stringResource(R.string.Check),
                    color = DormButtonColor.Blue,
                    enabled = confirmState,
                ) {
                }
            }
        }
    }
}
