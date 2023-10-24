package team.aliens.dms.android.feature.main.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onNavigateToStudyRoomList: () -> Unit,
    onNavigateToRemains: () -> Unit,
) {
    val viewModel: ApplicationViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(title = { Text(text = "신청") })
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Spacer(modifier = Modifier.height(DefaultVerticalSpace))
            ApplicationCard(
                title = stringResource(id = R.string.study_room),
                appliedTitle = uiState.appliedStudyRoom?.let { studyRoom ->
                    stringResource(
                        id = R.string.study_room_applied_text_format,
                        studyRoom.floor,
                        studyRoom.name,
                    )
                },
                description = stringResource(id = R.string.study_room_description),
                buttonText = stringResource(id = R.string.study_room_application),
                onButtonClick = onNavigateToStudyRoomList,
            )
            ApplicationCard(
                title = stringResource(id = R.string.remains_application),
                appliedTitle = uiState.appliedRemainsOption?.title,
                description = stringResource(id = R.string.remains_description),
                buttonText = stringResource(id = R.string.remains_application),
                onButtonClick = onNavigateToRemains,
            )
        }
    }
}

@Composable
private fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    appliedTitle: String?,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.outlinedCardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .topPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    color = DmsTheme.colorScheme.onSurface,
                    style = DmsTheme.typography.title2,
                )
                if (appliedTitle != null) {
                    RoundedButton(
                        onClick = { /*TODO*/ },
                        fillMinSize = false,
                    ) {
                        Text(text = appliedTitle)
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                text = description,
                style = DmsTheme.typography.body2,
                color = DmsTheme.colorScheme.onSurface,
            )
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = onButtonClick,
            ) {
                Text(text = buttonText)
            }
        }
    }
}
