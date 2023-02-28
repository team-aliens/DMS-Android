package team.aliens.dms_android.feature.pointlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.OverLine
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType

@Composable
fun PointList(
    modifier: Modifier = Modifier,
    points: MutableList<PointListEntity.PointValue>,
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
    ) {
        itemsIndexed(items = points) { index, point ->
            Spacer(modifier = Modifier.height(12.dp))
            Point(
                pointValue = point,
            )
            if(index == points.size - 1){
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun Point(
    pointValue: PointListEntity.PointValue,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .dormShadow(
                color = DormColor.Gray500,
                offsetY = 1.dp,
            )
            .background(
                color = DormColor.Gray100,
                shape = RoundedCornerShape(6.dp),
            )
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            Modifier.padding(horizontal = 24.dp),
        ) {

            OverLine(
                text = "${pointValue.date.split("-")[1]}월"
                + " ${pointValue.date.split("-")[2]}일",
                color = DormColor.Gray500,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Body5(
                text = pointValue.name,
                color = DormColor.Gray600,
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(end = 15.dp, bottom = 15.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            when(pointValue.pointType){
                PointType.BONUS -> {
                    Body4(
                        text = "+${pointValue.score}",
                        color = DormColor.DormPrimary
                    )
                }
                else -> {
                    Body4(
                        text = "-${pointValue.score}",
                        color = DormColor.Error
                    )
                }
            }
        }
    }
}