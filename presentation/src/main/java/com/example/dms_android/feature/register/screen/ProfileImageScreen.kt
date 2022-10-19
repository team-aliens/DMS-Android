package com.example.dms_android.feature.register.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.dialog.DormCustomDialog
import com.example.design_system.typography.Body4
import com.example.design_system.typography.NotoSansFamily
import com.example.dms_android.R
import com.google.android.material.bottomsheet.BottomSheetDialog

@Composable
fun ProfileImageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray100),
    ) {
        MainValue()
        PickImage()
        EnterNextPageView()
    }
}

@Composable
fun MainValue() {
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
                text = stringResource(id = R.string.ProfileImage),
                color = DormColor.Gray600,
            )
        }
    }
}

@Composable
fun PickImage() {

    Row(
        modifier = Modifier
            .padding(top = 76.dp)
            .height(150.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    painter = painterResource(id = R.drawable.addimage),
                    contentDescription = "사진 추가 이미지가 담기는 곳",
                )
            }
            Image(
                modifier = Modifier
                    .padding(start = 108.dp)
                    .height(30.dp)
                    .width(30.dp),
                painter = painterResource(id = R.drawable.addplusimage),
                contentDescription = "사진 추가 버튼",
            )
        }
    }
}

@Composable
fun EnterNextPageView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 233.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.SettingLater),
            color = DormColor.Gray600,
            fontFamily = NotoSansFamily,
            fontWeight = FontWeight(600),
        )
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
                modifier = Modifier
                    .height(37.dp)
                    .width(328.dp),
                text = stringResource(id = R.string.Next),
                color = DormButtonColor.Blue,
            ) {
                TODO("ViewModel Function")
            }
        }
    }
}

@Composable
fun BottomView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(148.dp)
                .fillMaxWidth()
                .background(color = DormColor.Gray100),
        ) {

        }
    }
}

@Preview
@Composable
fun ProfileImageScreenPreView() {
    ProfileImageScreen()
}
