package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.notice.NoticeDetailScreen

fun NavGraphBuilder.noticeNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.Notice.NoticeDetail,
        route = NavigationRoute.Notice.name,
    ){
        composable(
            route = NavigationRoute.Notice.NoticeDetail,
            arguments = listOf(
                navArgument(Extra.noticeId) { type = NavType.StringType },
            ),
        ) {
            val noticeId = it.arguments!!.getString(Extra.noticeId)
            if (noticeId != null) {
                NoticeDetailScreen(
                    navController = navController,
                    noticeId = noticeId,
                )
            }
        }
    }
}