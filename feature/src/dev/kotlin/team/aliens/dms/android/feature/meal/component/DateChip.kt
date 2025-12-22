package team.aliens.dms.android.feature.meal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB

@Composable
internal fun DateChip(
    modifier: Modifier = Modifier,
    date: LocalDate,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .background(
                color = DmsTheme.colorScheme.background,
                shape = CircleShape,
            )
            .border(width = 1.dp, color = DmsTheme.colorScheme.primary, shape = CircleShape)
            .padding(horizontal = 12.dp, vertical = 16.dp),
    ) {
        Text(
            text = "${date.year}.${date.monthValue}.${date.dayOfMonth} (${date.dayOfWeek.name})",
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.onPrimaryContainer,
        )
    }
}
