package team.aliens.dms_android.feature.home.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.component.DefaultAppliedTagSize
import team.aliens.design_system.component.LastAppliedItem
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.SubTitle2
import team.aliens.presentation.R

@Composable
internal fun ApplicationScreen(
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    studyRoomServiceEnabled: Boolean = false,
    remainsServiceEnabled: Boolean = false,
    applicationViewModel: ApplicationViewModel = hiltViewModel(),
) {
    val uiState by applicationViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormTheme.colors.background)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Space(space = 24.dp)
        Body1(text = stringResource(id = R.string.Application))
        Space(space = 40.dp)
        if (studyRoomServiceEnabled) {
           /* ApplicationCard(
                title = stringResource(id = R.string.StudyRoom),
                content = stringResource(id = R.string.StudyRoomApplyDescription),
                buttonText = stringResource(id = R.string.DoApplyStudyRoom),
                onButtonClick = onNavigateToStudyRooms,
                lastApplicationText = lastAppliedStudyRoom,
            )*/
            Space(space = 30.dp)
        }
        if (remainsServiceEnabled) {
           /* ApplicationCard(
                title = stringResource(id = R.string.Stay),
                content = stringResource(id = R.string.RemainApplyDescription),
                buttonText = stringResource(id = R.string.DoApplyRemain),
                onButtonClick = onNavigateToRemainsApplication, ,
                lastApplicationText = lastAppliedRemain,
            )*/
        }
    }
}

@Composable
fun ApplicationCard(
    title: String,
    content: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    lastApplicationText: String,
) {
    Column(
        modifier = Modifier
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
            if (lastApplicationText.isNotBlank()) {
                Space(ratio = 1f)
                LastAppliedItem(
                    modifier = DefaultAppliedTagSize,
                    text = lastApplicationText,
                )
            }
        }
        Body5(
            text = content,
        )
        DormContainedLargeButton(
            modifier = Modifier.height(40.dp),
            text = buttonText,
            color = DormButtonColor.Blue,
            onClick = onButtonClick,
        )
    }
}
