package team.aliens.dms.android.feature.main.application

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.shared.date.util.today

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onNavigateToStudyRoomList: () -> Unit,
    onNavigateToRemains: () -> Unit,
    onNavigateToOuting: () -> Unit,
) {
    val viewModel: ApplicationViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(title = { Text(text = stringResource(id = R.string.application)) })
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            ApplicationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .topPadding(),
                title = stringResource(id = R.string.study_room_application),
                appliedTitle = uiState.appliedStudyRoom?.let { studyRoom ->
                    stringResource(
                        id = R.string.format_study_room_applied_text,
                        studyRoom.floor,
                        studyRoom.name,
                    )
                },
                description = stringResource(id = R.string.study_room_description),
                buttonText = stringResource(id = R.string.study_room_do_application),
                onButtonClick = onNavigateToStudyRoomList,
            )
            ApplicationCard(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.remains_application),
                appliedTitle = uiState.appliedRemainsOption?.title,
                description = stringResource(id = R.string.remains_description),
                buttonText = stringResource(id = R.string.remains_do_application),
                onButtonClick = onNavigateToRemains,
            )
            ApplicationCard(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.outing_application),
                description = stringResource(id = R.string.outing_description),
                buttonText = stringResource(id = R.string.outing_do_application),
                onButtonClick = onNavigateToOuting,
            )
        }
    }
}

@Composable
private fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    appliedTitle: String? = null,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .animateContentSize()
            .horizontalPadding()
            .verticalPadding()
            .shadow(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.elevatedCardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Row(
                modifier = Modifier
                    .animateContentSize()
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
                // TODO: 태그 업데이트 로직 추가
//                AnimatedVisibility(
//                    visible = appliedTitle != null,
//                    enter = slideInVertically() + fadeIn(),
//                    exit = slideOutVertically() + fadeOut(),
//                ) {
//                    if (appliedTitle != null) {
//                        RoundedButton(
//                            onClick = {},
//                            fillMinSize = false,
//                            contentPadding = PaddingValues(
//                                horizontal = PaddingDefaults.Medium,
//                                vertical = PaddingDefaults.Small,
//                            ),
//                        ) {
//                            Text(text = appliedTitle)
//                        }
//                    }
//                }
            }
            Text(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding(),
                text = description,
                style = DmsTheme.typography.body3,
                color = DmsTheme.colorScheme.onSurface,
            )
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
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
