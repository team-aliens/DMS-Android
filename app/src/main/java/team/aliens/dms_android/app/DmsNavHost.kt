package team.aliens.dms_android.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun DmsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {/*
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = DmsDestinations.MAIN.route,
    ) {

    }*//*
    DestinationsNavHost(
        modifier = modifier,
        navGraph = NavGraphs
    )*/
    // DirectionDe
}

enum class DmsDestinations(
    val route: String,
) {
    MAIN(route = "main"),
    SIGN_IN(route = "sign-in"),
    SIGN_UP(route = "sign-up"),
    FIND_ID(route = "find-id"),
    ;
}
