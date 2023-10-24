package team.aliens.dms.android.feature.main.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding

@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onNavigateToStudyRoomList: () -> Unit,
    onNavigateToRemains: () -> Unit,
) {
    DmsScaffold(
        modifier = modifier,
    ) { padValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            item {
                ApplicationCard(
                    title = "잔류",
                    appliedTitle = "금요귀사",
                    description = "어쩌구저쩌구 네가 잔류하든 말든 나는 기숙사를 지킨다~어쩌구저쩌구 네가 잔류하든 말든 나는 기숙사를 지킨다~",
                    buttonText = "잔류 신청하기",
                    onButtonClick = {},
                )
            }
        }
    }
}

@Composable
private fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    appliedTitle: String,
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
            modifier = Modifier.fillMaxSize(),
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
                RoundedButton(
                    onClick = { /*TODO*/ },
                    fillMinSize = false,
                ) {
                    Text(text = appliedTitle)
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

/*

private sealed class ApplicationCardItem(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @StringRes val buttonTextRes: Int,
    val onButtonClick: () -> Unit,
    val currentAppliedOption: String?,
) {
    class StudyRoomService(
        onButtonClick: () -> Unit = { println("NOTHING HAPPENED") },
        currentAppliedOption: String? = null,
    ) : ApplicationCardItem(
        titleRes = R.string.study_room,
        descriptionRes = R.string.study_room_description,
        buttonTextRes = R.string.study_room_apply,
        onButtonClick = onButtonClick,
        currentAppliedOption = currentAppliedOption,
    )

    class RemainsService(
        onButtonClick: () -> Unit = { println("NOTHING HAPPENED") },
        currentAppliedOption: String? = null,
    ) : ApplicationCardItem(
        titleRes = R.string.remains_stay,
        descriptionRes = R.string.remains_description,
        onButtonClick = onButtonClick,
        buttonTextRes = R.string.remains_apply,
        currentAppliedOption = currentAppliedOption,
    )
}
*/

/*
    val uiState by applicationViewModel.stateFlow.collectAsStateWithLifecycle()
    val applicationItems =
        remember(uiState.currentAppliedStudyRoom, uiState.currentAppliedRemainsOption) {
            mutableStateListOf<ApplicationCardItem>()*//*.apply {
                if (studyRoomServiceEnabled) add(
                    ApplicationCardItem.StudyRoomService(
                        onButtonClick = onNavigateToStudyRooms,
                        currentAppliedOption = if (uiState.currentAppliedStudyRoom != null) {
                            "${uiState.currentAppliedStudyRoom?.floor}층 ${uiState.currentAppliedStudyRoom?.name}"
                        } else null,
                    ),
                )
                if (remainsServiceEnabled) add(
                    ApplicationCardItem.RemainsService(
                        onButtonClick = onNavigateToRemainsApplication,
                        currentAppliedOption = uiState.currentAppliedRemainsOption?.title,
                    ),
                )
            }*//*
        }

    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // todo 상단바 분리 필요
        Spacer(Modifier.height(24.dp))
        Body1(text = stringResource(R.string.Application))
        // todo end
        ApplicationCards(applicationItems)
    }*//*
@Composable
private fun ColumnScope.ApplicationCards(
    applicationItems: List<ApplicationCardItem>,
) {
    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.TopCenter,
    ) {
        VerticallyFadedLazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
        ) {
            items(applicationItems) { applicationItem ->
                ApplicationCard(
                    title = stringResource(applicationItem.titleRes),
                    description = stringResource(applicationItem.descriptionRes),
                    buttonText = stringResource(applicationItem.buttonTextRes),
                    onButtonClick = applicationItem.onButtonClick,
                    currentAppliedOption = applicationItem.currentAppliedOption,
                )
            }
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(36.dp)
                .dormGradientBackground(listFadeBrush),
        )
    }
}*//*

@Composable
private fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    currentAppliedOption: String?,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .dormShadow(
                DmsTheme.colors.primaryVariant,
            )
            .background(
                color = DmsTheme.colors.surface,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubTitle2(
                text = title,
            )
            if (currentAppliedOption != null) {
                Spacer(modifier = Modifier.weight(1f))
                LastAppliedItem(
                    modifier = DefaultAppliedTagSize,
                    text = currentAppliedOption,
                )
            }
        }
        Body5(
            text = description,
        )
        DormContainedLargeButton(
            text = buttonText,
            color = DormButtonColor.Blue,
            onClick = onButtonClick,
        )
    }
}
*/
