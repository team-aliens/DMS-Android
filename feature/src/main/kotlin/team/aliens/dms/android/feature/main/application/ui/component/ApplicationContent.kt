package team.aliens.dms.android.feature.main.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.card.ApplicationChipStyle
import team.aliens.dms.android.core.designsystem.card.DmsApplicationCard
import team.aliens.dms.android.feature.main.application.viewmodel.LateStudyStatusUi

@Composable
internal fun ApplicationContent(
    appliedTitle: String?,
    lateStudyAppliedTitle: String?,
    lateStudyStatus: LateStudyStatusUi?,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateLateStudyApplication: () -> Unit,
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
            Triple("새벽 자습 신청하기", R.drawable.img_3d_dawn, onNavigateLateStudyApplication),
            Triple("외출 신청하기", R.drawable.img_outing, onNavigateOutingApplication),
            Triple("봉사 활동 신청하기", R.drawable.img_volunteer, onNavigateVolunteerApplication),
        ).forEach { (title, icon, onClick) ->
            DmsApplicationCard(
                title = title,
                iconRes = icon,
                onClick = onClick,
                appliedTitle = when (title) {
                    "잔류" -> appliedTitle
                    "새벽 자습 신청하기" -> lateStudyAppliedTitle
                    else -> null
                },
                chipStyle = when (title) {
                    "새벽 자습 신청하기" -> when (lateStudyStatus) {
                        LateStudyStatusUi.SECOND_APPROVED -> ApplicationChipStyle.APPROVED
                        LateStudyStatusUi.REJECTED -> ApplicationChipStyle.REJECTED
                        LateStudyStatusUi.PENDING -> ApplicationChipStyle.PENDING
                        null -> ApplicationChipStyle.DEFAULT
                    }
                    else -> ApplicationChipStyle.DEFAULT
                },
            )
        }
    }
}
