package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable
import com.example.design_system.modifier.dormShadow
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body7

data class Notice(
    val title: String,
    val createAt: String,
)

@Composable
fun NoticeList(
    modifier: Modifier = Modifier,
    notices: List<Notice>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(items = notices) { index, notice ->
            Notice(
                notice = notice,
                index = index,
                onClick = onClick,
            )

            if (index != notices.size) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun Notice(
    notice: Notice,
    index: Int,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = DormColor.Gray100,
                shape = RoundedCornerShape(6.dp),
            )
            .fillMaxWidth()
            .height(70.dp)
            .dormShadow(
                color = DormColor.Gray100,
                offsetX = 1.dp,
                offsetY = 1.dp,
            )
            .dormClickable(
                rippleEnabled = true,
            ) {
                onClick(index)
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp)
        ) {
            Body4(
                text = notice.title,
                color = DormColor.Gray900,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Body7(
                text = notice.createAt,
                color = DormColor.Gray500,
            )
        }
    }
}

@Preview
@Composable
fun PreviewNotice() {
    val notices = listOf(
        Notice(
            title = "방 좀 치우고 살아주세요 ㅎㅎ",
            createAt = "2022.10.14 PM 08:33"
        ),
        Notice(
            title = "방에서도 마스크를 착용해주세요",
            createAt = "2022.10.15 PM 08:33"
        )
    )

    NoticeList(
        notices = notices,
        onClick = {}
    )
}