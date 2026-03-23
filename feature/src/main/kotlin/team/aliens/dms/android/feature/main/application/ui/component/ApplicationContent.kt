package team.aliens.dms.android.feature.main.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.card.DmsApplicationCard

@Composable
internal fun ApplicationContent(
    appliedTitle: String?,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp,
                vertical = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        listOf(
            Triple("잔류", R.drawable.img_home, onNavigateRemainApplication),
            Triple("외출 신청하기", R.drawable.img_outing, onNavigateOutingApplication),
            Triple("봉사 활동 신청하기", R.drawable.img_volunteer, onNavigateVolunteerApplication),
        ).forEach { (title, icon, onClick) ->
            DmsApplicationCard(
                title = title,
                iconRes = icon,
                onClick = onClick,
                appliedTitle = if (title == "잔류") appliedTitle else null,
            )
        }
    }
}
