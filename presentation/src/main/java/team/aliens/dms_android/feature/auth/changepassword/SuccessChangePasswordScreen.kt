package team.aliens.dms_android.feature.auth.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.Body2
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Composable
fun SuccessChangePasswordScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier.background(color = DormColor.Gray200)
    ) {
        TopBar(title = stringResource(id = R.string.ChangePassword)) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_success_change_password),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(40.dp))
            Body2(text = stringResource(id = R.string.SuccessChangePassword))
            Spacer(modifier = Modifier.fillMaxHeight(0.8f))
            DormContainedLargeButton(
                text = stringResource(id = R.string.Check),
                color = DormButtonColor.Blue,
            ) {
                // TODO implement onclick event
            }
        }
    }
}