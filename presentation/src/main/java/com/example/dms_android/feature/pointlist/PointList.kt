package com.example.dms_android.feature.pointlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable
import com.example.design_system.modifier.dormShadow
import com.example.design_system.typography.Body4
import com.example.design_system.typography.Body5
import com.example.domain.enums.PointType
import com.example.domain.enums.PointType.*

data class PointValue(
    val date: String,
    val content: String,
    val point: Int,
)

@Composable
fun PointList(
    modifier: Modifier = Modifier,
    points: List<PointValue>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(items = points) { index, point ->
            Point(
                pointValue = point,
                index = index,
                onClick = onClick
            )

            if (index != points.size) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun Point(
    pointValue: PointValue,
    index: Int,
    onClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
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
            Modifier.padding(horizontal = 24.dp),
        ) {
            Body4(
                text = pointValue.date,
                color = DormColor.Gray500,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Body5(
                text = pointValue.content,
                color = DormColor.Gray600,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.dp, bottom = 15.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            val ep: PointType = ALL
            var color: Color = DormColor.Error
            //TODO("VM 반환 값으로 색깔 변경 요함")
            if (ep == MINUS) {
                color = DormColor.Error
            } else if (ep == BONUS) {
                color = DormColor.DormPrimary
            }
            Body4(
                text = pointValue.point.toString(),
                color = color
            )
        }
    }
}