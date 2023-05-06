package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import team.aliens.dms_android.feature.notice.NoticeDetailScreen

fun NavGraphBuilder.noticeNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.NoticeDetail,
        route = NavigationRoute.Notice,
    ){
        composable(
            route = NavigationRoute.NoticeDetail,
            arguments = listOf(
                navArgument("noticeId") { type = NavType.StringType },
            ),
        ) {
            val noticeId = it.arguments!!.getString("noticeId")
            if (noticeId != null) {
                NoticeDetailScreen(
                    navController = navController,
                    noticeId = noticeId,
                )
            }
        }
    }
}