package team.aliens.dms.android.feature.vote.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.model.VotingItem
import java.time.LocalDateTime

@Composable
internal fun VoteItemContent(
    modifier: Modifier = Modifier,
    vote: Vote,
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    options: List<VotingItem>,
    students: List<Student>,
    modelStudents: List<Student>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        when (vote) {
            Vote.OPTION_VOTE -> {
                OptionContent(
                    title = title,
                    startTime = startTime,
                    endTime = endTime,
                    options = options,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            Vote.STUDENT_VOTE -> {
                StudentContent(
                    title = title,
                    startTime = startTime,
                    endTime = endTime,
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
                    startTime = startTime,
                    endTime = endTime,
                    students = modelStudents,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }
        }
    }
}
