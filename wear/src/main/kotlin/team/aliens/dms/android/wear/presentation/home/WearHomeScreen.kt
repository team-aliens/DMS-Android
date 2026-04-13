package team.aliens.dms.android.wear.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import team.aliens.dms.android.wear.R
import team.aliens.dms.android.wear.model.WearSnapshot
import team.aliens.dms.android.wear.presentation.component.WearScreen

@Composable
fun WearHomeScreen(
    snapshot: WearSnapshot,
    onNavigateMeal: () -> Unit,
    onNavigateStatus: () -> Unit,
    onNavigateNotice: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WearScreen(
        title = stringResource(R.string.wear_home_title),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.wear_home_description),
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = "${snapshot.primaryMealLabel} · ${snapshot.primaryMealMenu.firstOrNull().orEmpty()}",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "${snapshot.statusTitle} · ${snapshot.statusValue}",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = stringResource(R.string.wear_synced_at, snapshot.syncedAt),
            style = MaterialTheme.typography.bodySmall,
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onNavigateMeal) {
                Text(stringResource(R.string.wear_home_meal_action))
            }
            Button(onClick = onNavigateStatus) {
                Text(stringResource(R.string.wear_home_status_action))
            }
            Button(onClick = onNavigateNotice) {
                Text(stringResource(R.string.wear_home_notice_action))
            }
        }
    }
}
