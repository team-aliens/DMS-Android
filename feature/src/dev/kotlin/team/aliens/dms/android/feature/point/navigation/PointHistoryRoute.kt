package team.aliens.dms.android.feature.point.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.point.ui.PointHistory

@Composable
fun PointHistoryRoute(
    pointType: PointType,
    onBackClick: () -> Unit,
) {
    PointHistory(
        pointType = pointType,
        onBackClick = onBackClick,
    )
}
