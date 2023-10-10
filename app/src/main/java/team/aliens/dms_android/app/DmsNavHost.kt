package team.aliens.dms_android.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import team.aliens.dms_android.app.navigation.DmsNavGraph

@Composable
fun DmsNavHost(
    modifier: Modifier = Modifier,
    autoSignIn: Boolean,
) {
    DestinationsNavHost(
        modifier = modifier,
        navGraph = DmsNavGraph.root(autoSignIn = autoSignIn),
    )
}
