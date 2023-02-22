package team.aliens.dms_android.feature.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.component.LastAppliedItem
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.presentation.R

@Preview(
    showSystemUi = true,
)
@Composable
fun ApplicationScreenPreview() {
    ApplicationScreen(
        navController = rememberNavController(),
    )
}

@Composable
fun ApplicationScreen(
    navController: NavController,
) {
    // TODO 서버로부터 받아오기
    val studyRoomLastApplied = ""
    val remainLastApplied = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray200)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Body1(text = stringResource(id = R.string.Application))
        Spacer(modifier = Modifier.height(40.dp))
        ApplicationCard(
            title = stringResource(id = R.string.StudyRoom),
            content = stringResource(id = R.string.StudyRoomApplyDescription),
            buttonText = stringResource(id = R.string.DoApplyStudyRoom),
            onButtonClick = {
                navController.navigate(NavigationRoute.StudyRoom)
            },
            lastApplicationText = studyRoomLastApplied,
        )
        Spacer(modifier = Modifier.height(30.dp))
        ApplicationCard(
            title = stringResource(id = R.string.Stay),
            content = stringResource(id = R.string.RemainApplyDescription),
            buttonText = stringResource(id = R.string.DoApplyRemain),
            onButtonClick = {
                navController.navigate(NavigationRoute.RemainApplication)
            },
            lastApplicationText = remainLastApplied,
        )
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
                color = DormColor.Gray100,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SubTitle2(text = title)
            if (lastApplicationText.isNotBlank()) {
                Spacer(modifier = Modifier.weight(1f))
                LastAppliedItem(lastApplicationText)
            }
        }
        Body5(text = content)
        DormContainedLargeButton(
            modifier = Modifier.height(40.dp),
            text = buttonText,
            color = DormButtonColor.Blue,
            onClick = onButtonClick,
        )
    }
}
