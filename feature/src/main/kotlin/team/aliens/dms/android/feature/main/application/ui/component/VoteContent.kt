package team.aliens.dms.android.feature.main.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.card.DmsApplicationCard
import team.aliens.dms.android.core.ui.util.toDateString
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.Vote

@Composable
internal fun VoteContent(
    votes: ImmutableList<AllVoteSearch>,
    onNavigateVote: (AllVoteSearch) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 16.dp,
        ),
    ) {
        items(votes) { vote ->
            val icon = when (vote.voteType) {
                Vote.STUDENT_VOTE -> R.drawable.img_student_tag
                Vote.OPTION_VOTE -> R.drawable.img_percent
                Vote.APPROVAL_VOTE -> R.drawable.img_choice
                Vote.MODEL_STUDENT_VOTE -> R.drawable.img_model_student
            }
            DmsApplicationCard(
                title = vote.topicName,
                appliedTitle = null,
                period = "${vote.startTime.toDateString()} ~ ${vote.endTime.toDateString()}",
                description = vote.description,
                iconRes = icon,
                onClick = { onNavigateVote(vote) },
            )
        }
    }
}
