package team.aliens.dms.android.wear.presentation.status

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import team.aliens.dms.android.wear.R
import team.aliens.dms.android.wear.model.WearSnapshot
import team.aliens.dms.android.wear.presentation.component.WearScreen

@Composable
fun WearStatusScreen(
    snapshot: WearSnapshot,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WearScreen(
        title = stringResource(R.string.wear_status_title),
        modifier = modifier,
        actionLabel = stringResource(R.string.wear_back_action),
        onActionClick = onBackClick,
    ) {
        Text(
            text = snapshot.statusTitle,
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            text = snapshot.statusValue,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stringResource(R.string.wear_status_loading),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = stringResource(R.string.wear_synced_at, snapshot.syncedAt),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
