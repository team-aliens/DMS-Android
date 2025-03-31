package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ButtonColors
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun VotingStudentScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    //voteOption: AllVoteSearch,
) {
    val votingDetailViewModel: VotingViewModel = hiltViewModel()
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()
    var selectedFilter by remember { mutableStateOf("Day") }
    val filterOptions = listOf("1학년", "2학년", "3학년")

    LaunchedEffect(Unit) {
        //votingDetailViewModel.updateCheckVotingItem(voteOption = voteOption)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.voting_submit),
                            style = DmsTheme.typography.body2,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(team.aliens.dms.android.core.designsystem.R.drawable.chevronleft),
                            contentDescription = stringResource(R.string.voting_submit),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Text(
                text = "학생 투표",
                style = DmsTheme.typography.headline3,
            )

            MultiToggleButton(
                currentSelection = selectedFilter,
                toggleStates = filterOptions,
                onToggleChange = { selectedFilter = it }
            )
            LazyColumn {
                items(uiState.votingTopicCheckList) {
//                    StudentProfile(
//                        id = ,
//                        studentGcn = ,
//                        name = ,
//                        profileImageUrl = ,
//                        onclick =
//                    )
                }
            }
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = navigator::navigateUp,
            ) {
                Text(text = "투표하기")
            }
        }
    }
}

@Composable
fun MultiToggleButton(
    currentSelection: String,
    toggleStates: List<String>,
    onToggleChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        toggleStates.forEachIndexed { _, toggleState ->
            val isSelected = currentSelection.equals(toggleState, ignoreCase = true)
            val backgroundColor = if (isSelected) DmsTheme.colorScheme.primary else Color.White
            val textColor = if (isSelected) Color.White else DmsTheme.colorScheme.primary

            OutlinedButton(
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(4.dp),
                        ),
                    onClick = { onToggleChange(toggleState) },
                    border = BorderStroke(1.dp, DmsTheme.colorScheme.primary),
                    shape = RoundedCornerShape(4.dp),
                ) {
                Text(
                    text = toggleState,
                    color = textColor,
                )
            }
        }
    }
}

@Composable
private fun StudentProfile(
    id: UUID,
    studentGcn: Long,
    name: String,
    profileImageUrl: String,
    onclick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalPadding()
            .verticalPadding(14.dp)
            .clickable(onClick = onclick)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            model = profileImageUrl,
            contentDescription = "student_image",
        )
        Text(
            text = "$studentGcn $name",
        )
    }
}

