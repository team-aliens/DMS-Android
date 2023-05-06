package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.image.ConfirmImageScreen
import team.aliens.dms_android.feature.pointlist.PointListScreen
import team.aliens.dms_android.util.SelectImageType

fun NavGraphBuilder.myPageNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.MyPage.PointList,
        route = NavigationRoute.MyPage.name,
    ){
        composable(NavigationRoute.MyPage.PointList) {
            PointListScreen(
                navController = navController,
            )
        }

        composable(
            route = NavigationRoute.MyPage.ConfirmImage + "/{${Extra.selectedImageType}}",
            arguments = listOf(
                navArgument(Extra.selectedImageType) {
                    defaultValue = SelectImageType.SELECT_FROM_GALLERY.ordinal
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->

            val selectImageType = navBackStackEntry.arguments?.getInt(Extra.selectedImageType)
                ?: SelectImageType.SELECT_FROM_GALLERY.ordinal

            ConfirmImageScreen(
                selectImageType = selectImageType,
                navController = navController,
            )
        }
    }
}