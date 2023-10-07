package team.aliens.dms_android.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun DmsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = DmsDestinations.MAIN.route,
    ) {

    }
}

enum class DmsDestinations(
    val route: String,
) {
    MAIN(route = "main"),
    SIGN_IN(route = "sign-in"),
    SIGN_UP(route = "sign-up"),

    ;
}
