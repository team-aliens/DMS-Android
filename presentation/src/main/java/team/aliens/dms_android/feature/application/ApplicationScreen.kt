package team.aliens.dms_android.feature.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.DormTypography
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
        )
        Spacer(modifier = Modifier.height(30.dp))
        ApplicationCard(
            title = stringResource(id = R.string.Stay),
            content = stringResource(id = R.string.StayApplyDescription),
            buttonText = stringResource(id = R.string.DoApplyStay),
            onButtonClick = {
                // TODO 잔류 신청 화면으로 이동 로직 구현
            },
        )
    }
}

@Composable
fun ApplicationCard(
    title: String,
    content: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    lastApplied: String = "",
) {
    Column(
        modifier = Modifier
            .height(192.dp)
            .background(
                color = DormColor.Gray100,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.74f),
                text = title,
                style = DormTypography.subtitle2,
                color = DormColor.Gray900,
            )
            if (lastApplied.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .size(
                            width = 92.dp,
                            height = 34.dp,
                        )
                        .background(
                            color = DormColor.Lighten200,
                            shape = RoundedCornerShape(100)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    ButtonText(
                        text = lastApplied,
                        color = DormColor.DormPrimary,
                    )
                }
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