package team.aliens.dms.android.feature.vote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.startPadding
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.voting.model.VotingItem

@Composable
internal fun OptionContent(
    modifier: Modifier = Modifier,
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    options: List<VotingItem>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TitleContent(
            title = title,
            startTime = startTime,
            endTime = endTime,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.4.dp,
            color = DmsTheme.colorScheme.onSurfaceVariant,
        )
        LazyColumn {
            items(
                items = options,
                key = { it.id },
            ) { option ->
                OptionItem(
                    title = option.votingOptionName,
                    selected = option.id.toString() == selectItem,
                    onClick = { onSelect(option.id.toString()) },
                )
            }
        }
    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (selected) {
        DmsTheme.colorScheme.surfaceVariant
    } else {
        DmsTheme.colorScheme.background
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.startPadding(12.dp),
            text = title,
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(DmsIcon.Forward),
            tint = DmsTheme.colorScheme.scrim,
            contentDescription = null,
        )
    }
}
