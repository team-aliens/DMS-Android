package team.aliens.dms_android.feature.apply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.feature.navigator.NavigationRoute
import team.aliens.presentation.R

@Composable
fun ApplicationMainScreen(
    navController: NavController,
) {
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DormColor.Gray200)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Body1(text = mContext.getString(R.string.Application))
        Spacer(modifier = Modifier.height(40.dp))
        ApplicationCard(
            title = mContext.getString(R.string.StudyRoom),
            content = mContext.getString(R.string.StudyRoomApplyDescription),
            buttonText = mContext.getString(R.string.DoApplyStudyRoom),
            buttonOnClick = {
                navController.navigate(NavigationRoute.StudyRoom)
            },
        )
        Spacer(modifier = Modifier.height(30.dp))
        ApplicationCard(
            title = mContext.getString(R.string.Stay),
            content = mContext.getString(R.string.StayApplyDescription),
            buttonText = mContext.getString(R.string.DoApplyStay),
            buttonOnClick = {
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
    buttonOnClick: () -> Unit,
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
        SubTitle2(text = title)
        Body5(text = content)
        DormContainedLargeButton(
            modifier = Modifier.height(40.dp),
            text = buttonText,
            color = DormButtonColor.Blue,
            onClick = buttonOnClick,
        )
    }
}