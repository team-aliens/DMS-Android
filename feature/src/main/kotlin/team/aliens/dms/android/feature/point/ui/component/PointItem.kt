package team.aliens.dms.android.feature.point.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.shared.date.toLocalDate

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
internal fun PointItem(
    name: String,
    point: Int,
    date: String,
    pointType: PointType,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        val (icon, pointColor) = if (pointType == PointType.BONUS) {
            Pair(

                R.drawable.ic_plus,
                DmsTheme.colorScheme.onPrimaryContainer,
            )
        } else {
            Pair(R.drawable.ic_minus, DmsTheme.colorScheme.onErrorContainer)
        }
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
        Text(
            text = " ${point}점",
            style = DmsTheme.typography.bodyB,
            color = pointColor,
        )
        Text(
            text = "${date.toLocalDate()}",
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.inverseSurface,
        )
    }
}
