package team.aliens.dms.android.feature.main.mypage.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.titleB
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.point.model.PointType

@Composable
internal fun PointCards(
    modifier: Modifier = Modifier,
    bonusPoint: Int,
    minusPoint: Int,
    onNavigatePointHistory: (PointType) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PointCard(
            modifier = Modifier.weight(1f),
            point = bonusPoint,
            pointType = PointType.PLUS,
            onClick = { onNavigatePointHistory(PointType.PLUS) },
        )
        PointCard(
            modifier = Modifier.weight(1f),
            point = minusPoint,
            pointType = PointType.MINUS,
            onClick = { onNavigatePointHistory(PointType.MINUS) },
        )
    }
}

@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    point: Int,
    pointType: PointType,
    onClick: () -> Unit,
) {
    val (text, textColor, backgroundColor) = when (pointType) {
        PointType.PLUS -> Triple("상점", DmsTheme.colorScheme.secondary, DmsTheme.colorScheme.primary)
        PointType.MINUS -> Triple("벌점", DmsTheme.colorScheme.onErrorContainer, DmsTheme.colorScheme.error)
        else -> Triple("", DmsTheme.colorScheme.onSurface, DmsTheme.colorScheme.surface)
    }

    OutlinedCard(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = backgroundColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = textColor,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = text,
                style = DmsTheme.typography.bodyM,
                color = textColor,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = point.toString(),
                style = DmsTheme.typography.titleB,
                color = textColor,
                textAlign = TextAlign.Right,
            )
        }
    }
}
