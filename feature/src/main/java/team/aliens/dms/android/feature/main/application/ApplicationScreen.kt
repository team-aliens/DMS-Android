package team.aliens.dms.android.feature.main.application

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.designsystem.button.DormButtonColor
import team.aliens.dms.android.designsystem.button.DormContainedLargeButton
import team.aliens.dms.android.designsystem.component.DefaultAppliedTagSize
import team.aliens.dms.android.designsystem.component.LastAppliedItem
import team.aliens.dms.android.designsystem.layout.VerticallyFadedLazyColumn
import team.aliens.dms.android.designsystem.modifier.dormGradientBackground
import team.aliens.dms.android.designsystem.modifier.dormShadow
import team.aliens.dms.android.designsystem.theme.DormTheme
import team.aliens.dms.android.designsystem.typography.Body5
import team.aliens.dms.android.designsystem.typography.SubTitle2
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.listFadeBrush
import team.aliens.dms.android.feature.main.navigation.MainNavigator

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

@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    // TODO: implement cacher
    // studyRoomServiceEnabled: Boolean = false,
    // remainsServiceEnabled: Boolean = false,
    // applicationViewModel: ApplicationViewModel = hiltViewModel(),
) {/*
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
    }*/
}

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
}

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
                DormTheme.colors.primaryVariant,
            )
            .background(
                color = DormTheme.colors.surface,
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
