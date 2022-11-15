package com.example.dms_android.feature.pointlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormShadow
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body5
import com.example.design_system.typography.Headline1
import com.example.design_system.typography.OverLine
import com.example.dms_android.R
import com.example.dms_android.util.TopBar

@Composable
fun PointListScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
    ) {
        TopBar(title = stringResource(id = R.string.CheckPoint))
        DialogBox()
        PointListValue()
    }
}

@Composable
fun DialogBox() {

    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 50.dp)
    ) {

        DormContainedLargeButton(
            modifier = Modifier
                .width(80.dp)
                .height(44.dp),
            text = stringResource(id = R.string.ALL),
            color = DormButtonColor.Blue,
            enabled = true,
            onClick = {},
        )
        Spacer(
            modifier = Modifier
                .width(15.dp)
        )
        DormContainedLargeButton(
            modifier = Modifier
                .width(80.dp)
                .height(44.dp),
            text = stringResource(id = R.string.PlusPoint),
            color = DormButtonColor.Blue,
            enabled = false,
            onClick = {
                // TODO("Change Category")
            },
        )
        Spacer(
            modifier = Modifier
                .width(15.dp)
        )
        DormContainedLargeButton(
            modifier = Modifier
                .width(80.dp)
                .height(44.dp),
            text = stringResource(id = R.string.MinusPoint),
            color = DormButtonColor.Blue,
            enabled = false,
            onClick = {
                // TODO("Change Category")
            },
        )
    }
}

@Composable
fun PointListValue() {

    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .height(44.dp)
            )
            Headline1(text = stringResource(id = R.string.ExampleTotalPoint))
        }
        Spacer(
            modifier = Modifier
                .height(44.dp)
        )
        val point = listOf(
            PointValue(
                date = "8월 12일",
                content = "기숙사 야간 무단 외출",
                point = -10,
            ),
            PointValue(
                date = "8월 15일",
                content = "반입 금지 물품 배달",
                point = -5,
            ),
        )
        PointList(points = point, onClick = {})
    }
}

@Preview
@Composable
fun PointListPreView() {
    PointListScreen()
}