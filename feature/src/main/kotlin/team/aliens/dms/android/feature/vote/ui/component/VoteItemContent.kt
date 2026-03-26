package team.aliens.dms.android.feature.vote.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.model.VotingItem
import team.aliens.dms.android.shared.date.toLocalDateTime

@Composable
internal fun VoteItemContent(
    vote: Vote,
    title: String,
    startTime: String,
    endTime: String,
    options: ImmutableList<VotingItem>,
    students: ImmutableList<Student>,
    modelStudents: ImmutableList<Student>,
    selectItem: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        when (vote) {
            Vote.OPTION_VOTE -> {
                OptionContent(
                    title = title,
                    startTime = startTime.toLocalDateTime(),
                    endTime = endTime.toLocalDateTime(),
                    options = options,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            Vote.STUDENT_VOTE -> {
                StudentContent(
                    title = title,
                    startTime = startTime.toLocalDateTime(),
                    endTime = endTime.toLocalDateTime(),
                    students = students,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            Vote.APPROVAL_VOTE -> {
                ApprovalContent(
                    title = title,
                    options = options,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            Vote.MODEL_STUDENT_VOTE -> {
                StudentContent(
                    title = title,
                    startTime = startTime.toLocalDateTime(),
                    endTime = endTime.toLocalDateTime(),
                    students = modelStudents,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }
        }
    }
}
