package team.aliens.dms.android.wear.presentation.meal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import team.aliens.dms.android.wear.R
import team.aliens.dms.android.wear.model.WearSnapshot
import team.aliens.dms.android.wear.presentation.component.WearBulletList
import team.aliens.dms.android.wear.presentation.component.WearScreen

@Composable
fun WearMealScreen(
    snapshot: WearSnapshot,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WearScreen(
        title = stringResource(R.string.wear_meal_title),
        modifier = modifier,
        actionLabel = stringResource(R.string.wear_back_action),
        onActionClick = onBackClick,
    ) {
        Text(
            text = snapshot.primaryMealLabel,
            style = MaterialTheme.typography.titleSmall,
        )
        WearBulletList(items = snapshot.primaryMealMenu)
        Text(
            text = stringResource(R.string.wear_synced_at, snapshot.syncedAt),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
