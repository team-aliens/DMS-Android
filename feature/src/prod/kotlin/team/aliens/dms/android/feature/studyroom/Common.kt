package team.aliens.dms.android.feature.studyroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.shared.model.Sex

@Composable
fun StudyRoomCard(
    modifier: Modifier = Modifier,
    studyRoom: StudyRoom,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .horizontalPadding()
            .verticalPadding(PaddingDefaults.ExtraSmall)
            .shadow(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        onClick = onClick,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                Text(
                    modifier = Modifier
                        .startPadding()
                        .topPadding(),
                    text = stringResource(
                        id = R.string.format_study_room_floor,
                        studyRoom.floor,
                    ),
                    style = DmsTheme.typography.body2,
                    color = DmsTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier
                        .topPadding()
                        .weight(1f),
                    text = studyRoom.name,
                    style = DmsTheme.typography.body2,
                    color = DmsTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier
                        .topPadding()
                        .endPadding(),
                    text = stringResource(
                        id = R.string.format_study_room_headcount,
                        studyRoom.inUseHeadcount,
                        studyRoom.totalAvailableSeat,
                    ),
                    style = DmsTheme.typography.body2,
                    color = DmsTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                modifier = Modifier
                    .horizontalPadding()
                    .verticalPadding(),
                text = stringResource(
                    id = R.string.format_study_room_available_for,
                    studyRoom.availableGrade,
                    studyRoom.availableSex.text,
                ),
                style = DmsTheme.typography.body2,
                color = DmsTheme.colorScheme.primary,
            )
        }
    }
}

@Stable
private val Sex.text: String
    @Composable get() = stringResource(
        id = when (this) {
            Sex.MALE -> R.string.sex_male
            Sex.FEMALE -> R.string.sex_female
            Sex.ALL -> R.string.sex_all
        },
    )
