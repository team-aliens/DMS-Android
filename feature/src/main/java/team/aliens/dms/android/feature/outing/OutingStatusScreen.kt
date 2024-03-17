package team.aliens.dms.android.feature.outing

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.feature.outing.navigation.OutingNavigator

@Destination
@Composable
fun OutingStatusScreen(
    navigator: OutingNavigator,
    viewModel: OutingViewModel = hiltViewModel(),
) {
    Scaffold(

    ) { padValues ->

    }
}
