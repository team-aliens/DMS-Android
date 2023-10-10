package team.aliens.dms_android.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun DmsNavHost(
    modifier: Modifier = Modifier,
) {
    DestinationsNavHost(
        modifier = modifier,
        navGraph = NavGraphs.root,
    )
}
