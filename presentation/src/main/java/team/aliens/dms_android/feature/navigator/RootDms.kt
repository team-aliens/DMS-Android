package team.aliens.dms_android.feature.navigator

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordScreen
import team.aliens.dms_android.feature.auth.comparepassword.ComparePasswordScreen
import team.aliens.dms_android.feature.auth.login.LoginScreen
import team.aliens.dms_android.feature.auth.login.SignInViewEffect
import team.aliens.dms_android.feature.image.ConfirmImageScreen
import team.aliens.dms_android.feature.mypage.MyPageChangePasswordScreen
import team.aliens.dms_android.feature.notice.NoticeDetailScreen
import team.aliens.dms_android.feature.pointlist.PointListScreen
import team.aliens.dms_android.feature.register.ui.email.SignUpEmailScreen
import team.aliens.dms_android.feature.register.ui.email.SignUpEmailVerifyScreen
import team.aliens.dms_android.feature.register.ui.id.SignUpIdScreen
import team.aliens.dms_android.feature.register.ui.last.SignUpPolicyScreen
import team.aliens.dms_android.feature.register.ui.last.SignUpProfileScreen
import team.aliens.dms_android.feature.register.ui.password.SignUpPasswordScreen
import team.aliens.dms_android.feature.register.ui.school.SignUpSchoolQuestionScreen
import team.aliens.dms_android.feature.register.ui.school.SignUpVerifySchoolScreen
import team.aliens.dms_android.feature.remain.RemainApplicationScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomDetailScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomListScreen
import team.aliens.dms_android.util.SelectImageType

@Composable
fun RootDms(
    route: String,
) {

    val navController = rememberNavController()

    val scaffoldState = rememberScaffoldState()

    NavHost(
        navController = navController,
        startDestination = route,
    ) {

        composable(NavigationRoute.Login) {
            LoginScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Main) {
            DmsApp(
                navController = navController,
                scaffoldState = scaffoldState,
            )
        }

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

        composable(NavigationRoute.PointList) {
            PointListScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.ChangePassword) {
            ChangePasswordScreen()
        }

        composable(NavigationRoute.MyPageChangePassword) {
            MyPageChangePasswordScreen(
                navController = navController,
            )
        }

        composable(
            route = NavigationRoute.StudyRoomDetail,
            arguments = listOf(
                navArgument("seatId") { type = NavType.StringType },
            ),
        ) {
            val roomId = it.arguments!!.getString("seatId")
            if (roomId != null) {
                StudyRoomDetailScreen(
                    navController = navController,
                    roomId = roomId,
                )
            }
        }

        composable(NavigationRoute.StudyRoom) {
            StudyRoomListScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.RemainApplication) {
            RemainApplicationScreen(
                navController = navController,
            )
        }

        composable(
            route = NavigationRoute.ConfirmImage + "/{selectImageType}",
            arguments = listOf(
                navArgument("selectImageType") {
                    defaultValue = SelectImageType.SELECT_FROM_GALLERY.ordinal
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->

            val selectImageType = navBackStackEntry.arguments?.getInt("selectImageType")
                ?: SelectImageType.SELECT_FROM_GALLERY.ordinal

            ConfirmImageScreen(
                selectImageType = selectImageType,
                navController = navController,
            )
        }

        composable(
            route = NavigationRoute.ComparePassword,
        ){
            ComparePasswordScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.VerifySchool) {
            SignUpVerifySchoolScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SchoolQuestion) {
            SignUpSchoolQuestionScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpEmail){
            SignUpEmailScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpEmailVerify){
            SignUpEmailVerifyScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpId){
            SignUpIdScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpPassword){
            SignUpPasswordScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpProfile){
            SignUpProfileScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpPolicy){
            SignUpPolicyScreen(
                navController = navController,
            )
        }
    }
}
