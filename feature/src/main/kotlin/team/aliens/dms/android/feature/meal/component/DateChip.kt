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
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.core.ui.util.toLocale

@Composable
internal fun DateChip(
    dateText: String,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .background(
                color = DmsTheme.colorScheme.background,
                shape = CircleShape,
            )
            .border(width = 1.dp, color = DmsTheme.colorScheme.primary, shape = CircleShape)
            .clickable(onClick = onDateClick)
            .padding(horizontal = 12.dp, vertical = 16.dp),
    ) {
        Text(
            text = dateText,
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.onPrimaryContainer,
        )
    }
}
