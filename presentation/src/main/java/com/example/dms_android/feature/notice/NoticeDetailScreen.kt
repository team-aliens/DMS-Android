package com.example.dms_android.feature.notice

import android.view.View
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body5
import com.example.design_system.typography.Caption
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R

@Composable
fun NoticeDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200)
    ) {
        Topbar()
        NoticeDetailValue()
    }
}

@Composable
fun Topbar() {
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(color = DormColor.Gray100),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .width(25.dp)
        )
        Image(
            modifier = Modifier
                .size(30.dp),
            painter = painterResource(id = DormIcon.BackArrow.drawableId), 
            contentDescription = stringResource(id = R.string.backButton)
        )
        Spacer(
            modifier = Modifier
                .width(45.dp)
        )
        SubTitle2(text = stringResource(id = R.string.Notice))
    }
}

@Composable
fun NoticeDetailValue() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(
            modifier = Modifier
                .height(55.dp)
        )
        SubTitle2(
            text = stringResource(id = R.string.NoticeTitle)
        )
        Spacer(
            modifier = Modifier
                .height(25.dp)
        )
        Caption(text = stringResource(id = R.string.NoticeTime))
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(end = 23.dp)
                .background(DormColor.Gray300)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Box(
            modifier = Modifier
                .padding(end = 23.dp)
        ) {
            Body5(
                text = stringResource(id = R.string.NoticeContent)
            )
        }
    }
}

@Preview
@Composable
fun NoticeDetailPreView() {
    NoticeDetailScreen()
}