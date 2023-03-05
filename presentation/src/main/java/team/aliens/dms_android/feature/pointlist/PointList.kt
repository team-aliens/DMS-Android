package team.aliens.dms_android.feature.pointlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.OverLine
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType

@Composable
fun PointList(
    modifier: Modifier = Modifier,
    points: List<PointListEntity.PointValue>,
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 10.dp,
        )
    ) {
        itemsIndexed(items = points) { index, point ->
            Spacer(modifier = Modifier.height(12.dp))
            Point(
                pointValue = point,
            )
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
                color = DormTheme.colors.secondaryVariant,
                offsetY = 1.dp,
            )
            .clip(
                RoundedCornerShape(10.dp),
            )
            .background(
                color = DormTheme.colors.surface,
            )
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            Modifier.padding(horizontal = 24.dp),
        ) {

            val pointDate = pointValue.date.split("-")

            OverLine(
                text = "${pointDate[1]}월"
                        + " ${pointDate[2]}일",
                color = DormTheme.colors.primaryVariant,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Body5(
                text = pointValue.name,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.dp, bottom = 15.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            when (pointValue.pointType) {
                PointType.BONUS -> {
                    Body4(
                        text = "+${pointValue.score}",
                        color = DormTheme.colors.primary,
                    )
                }
                else -> {
                    Body4(
                        text = "-${pointValue.score}",
                        color = DormTheme.colors.error,
                    )
                }
            }
        }
    }
}