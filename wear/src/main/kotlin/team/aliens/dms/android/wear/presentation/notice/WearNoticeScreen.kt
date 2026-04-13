package team.aliens.dms.android.wear.presentation.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import team.aliens.dms.android.wear.R
import team.aliens.dms.android.wear.model.WearSnapshot
import team.aliens.dms.android.wear.presentation.component.WearScreen

@Composable
fun WearNoticeScreen(
    snapshot: WearSnapshot,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WearScreen(
        title = stringResource(R.string.wear_notice_title),
        modifier = modifier,
        actionLabel = stringResource(R.string.wear_back_action),
        onActionClick = onBackClick,
    ) {
        if (snapshot.notices.isEmpty()) {
            Text(
                text = stringResource(R.string.wear_notice_empty),
                style = MaterialTheme.typography.bodySmall,
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                snapshot.notices.forEach { notice ->
                    Text(
                        text = buildString {
                            if (notice.isImportant) append("[중요] ")
                            append(notice.title)
                            append(" · ")
                            append(notice.dateText)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = if (notice.isImportant) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}
