package team.aliens.dms_android.feature.pointlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body4
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.OverLine
import team.aliens.domain.model._common.PointType
import team.aliens.domain.model.point.FetchPointsOutput

@Composable
fun PointList(
    modifier: Modifier = Modifier,
    points: List<FetchPointsOutput.PointInformation>,
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 10.dp,
        ),
    ) {
        itemsIndexed(items = points) { index, point ->
            this@LazyColumn.Space(space = 12.dp)
            Point(
                pointValue = point,
            )
        }
    }
}

@Composable
private fun Point(
    pointValue: FetchPointsOutput.PointInformation,
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
                text = "${pointDate[1]}월" + " ${pointDate[2]}일",
                color = DormTheme.colors.primaryVariant,
            )

            Space(space = 4.dp)

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
            when (pointValue.type) {
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