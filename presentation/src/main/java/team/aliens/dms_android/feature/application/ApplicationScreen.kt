package team.aliens.dms_android.feature.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.component.DefaultAppliedTagSize
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.design_system.component.LastAppliedItem
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.dms_android.viewmodel.ApplicationViewModel
import team.aliens.presentation.R

@Composable
fun ApplicationScreen(
    navController: NavController,
    applicationViewModel: ApplicationViewModel = hiltViewModel(),
) {

    var lastAppliedStudyRoom by remember { mutableStateOf("") }
    var lastAppliedRemain by remember { mutableStateOf("") }

    val isStudyRoomServiceEnabled = LocalAvailableFeatures.current[Extra.isStudyRoomEnabled]
    val isRemainServiceEnabled = LocalAvailableFeatures.current[Extra.isRemainServiceEnabled]

    LaunchedEffect(Unit) {
        applicationViewModel.run {

            fetchCurrentStudyRoomOption()
            fetchCurrentRemainOption()

            uiState.collect {
                lastAppliedStudyRoom = it.currentStudyRoomOption
                lastAppliedRemain = it.currentRemainOption
            }
        }
    }

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
        if (isStudyRoomServiceEnabled!!) {
            ApplicationCard(
                title = stringResource(id = R.string.StudyRoom),
                content = stringResource(id = R.string.StudyRoomApplyDescription),
                buttonText = stringResource(id = R.string.DoApplyStudyRoom),
                onButtonClick = {
                    navController.navigate(NavigationRoute.Application.StudyRoom)
                },
                lastApplicationText = lastAppliedStudyRoom,
            )
            Space(space = 30.dp)
        }
        if (isRemainServiceEnabled!!) {
            ApplicationCard(
                title = stringResource(id = R.string.Stay),
                content = stringResource(id = R.string.RemainApplyDescription),
                buttonText = stringResource(id = R.string.DoApplyRemain),
                onButtonClick = {
                    navController.navigate(NavigationRoute.Application.RemainApplication)
                },
                lastApplicationText = lastAppliedRemain,
            )
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
