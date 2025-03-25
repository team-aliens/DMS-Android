package team.aliens.dms.android.feature.voting

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun VotingModelStudentScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    votingDetailViewModel: VotingViewModel = hiltViewModel(),
) {
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.voting_submit))
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
        }
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
            StudentButton(text = "1학년")
            StudentButton(text = "2학년")
            StudentButton(text = "3학년")
            LazyColumn {
                items(uiState.modelStudentCandidates) {
                    ModelStudentCard(
                        id = it.id,
                        studentGcn = it.studentGcn,
                        name = it.name,
                        profileImageUrl = it.name,
                    )
                }
            }
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = { /* 상태 값 변경 */ },
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
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 14.dp),
            text = text,
        )
    }
}

@Composable
private fun ModelStudentCard(
    id: UUID,
    studentGcn: Long,
    name: String,
    profileImageUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalPadding()
            .verticalPadding(14.dp)
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


