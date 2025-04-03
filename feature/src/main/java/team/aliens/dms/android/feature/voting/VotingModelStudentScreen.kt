package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.designsystem.ButtonColors
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun VotingModelStudentScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
) {
    val votingDetailViewModel: VotingViewModel = hiltViewModel()
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()

    votingDetailViewModel.updateModelStudentList(LocalDate.now())

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
                text = "모범학생 투표",
                style = DmsTheme.typography.headline3,
            )
            Row(
                modifier = modifier
                    .horizontalPadding(),
            ) {
                StudentButton(text = "1학년")
                StudentButton(text = "2학년")
                StudentButton(text = "3학년")
            }
            Log.d("TEST", uiState.modelStudentCandidates.toString())
            LazyColumn {
                items(uiState.modelStudentCandidates) {
                    StudentProfile(
                        studentGcn = it.studentGcn.toString(),
                        name = it.name,
                        profileImageUrl = it.profileImageUrl,
                        onClick = {
//                            votingDetailViewModel.postIntent(
//                                VotingIntent.CreateVoteTable(
//                                    votingTopicId = voteOption.id,
//                                    selectedId = it.id,
//                                )
//                            )
                        }
                    )
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
private fun StudentButton(
    modifier: Modifier = Modifier,
    text: String,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = {},
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = DmsTheme.colorScheme.primary,
            disabledContainerColor = DmsTheme.colorScheme.primary,
            disabledContentColor = Color.White,
        ),
        border = BorderStroke(1.dp, DmsTheme.colorScheme.primary),
        shape = RoundedCornerShape(4.dp),

    ) {
        Text(
            text = text,
        )
    }
}

@Composable
private fun StudentProfile(
    studentGcn: String,
    name: String,
    profileImageUrl: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) DmsTheme.colorScheme.primary else Color.Unspecified

    HorizontalDivider(
        thickness = 1.dp,
        color = DmsTheme.colorScheme.onSurfaceVariant,
    )
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color),
        interactionSource = interactionSource,
        onClick = onClick,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            model = profileImageUrl,
            contentDescription = "student_image",
            alignment = Alignment.CenterStart,
        )
        Text(
            text = "$studentGcn $name",
            textAlign = TextAlign.End,
            color = Color.Black,
        )
    }
}


