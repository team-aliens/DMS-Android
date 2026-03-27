package team.aliens.dms.android.feature.vote.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.lBodyB
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.ui.util.toDateString

@Composable
internal fun TitleContent(
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = DmsTheme.typography.lBodyB,
            color = DmsTheme.colorScheme.tertiaryContainer,
        )
        Text(
            text = "${startTime.toDateString()} ~ ${endTime.toDateString()}",
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
    }
}
