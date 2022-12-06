package com.example.dms_android.feature.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.design_system.color.DormColor
import com.example.design_system.component.Notice
import com.example.design_system.component.NoticeList
import com.example.design_system.icon.DormIcon
import com.example.design_system.typography.Body4
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R

@Composable
fun NoticeScreen(
    navController: NavController
) {

    val notices = listOf(
        Notice(
            title = "방 좀 치우고 살아주세요 ㅎㅎ",
            createAt = "2022.10.14 PM 08:33",
        ),
        Notice(
            title = "방에서도 마스크를 착용해주세요",
            createAt = "2022.10.15 PM 08:33",
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
    ) {
        NoticeTopBar()
        NoticeOrderButton()
        NoticeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            notices = notices,
            onClick = {},
        )
    }
}

@Composable
fun NoticeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        SubTitle2(text = stringResource(id = R.string.Notice))
    }
}

@Composable
fun NoticeOrderButton() {
    Button(
        onClick = { /*TODO*/ },
        colors= ButtonDefaults.buttonColors(backgroundColor = DormColor.Gray100),
        modifier = Modifier
            .padding(start = 20.dp)
            .border(
                color = DormColor.Gray600,
                width = 1.dp,
                shape = RoundedCornerShape(15)
            )
            .size(width = 82.dp, height = 40.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.New),
            )
        }
    }
}