package team.aliens.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.util.UUID
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastHost
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordScreen
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordVerifyEmailScreen
import team.aliens.dms_android.feature.auth.changepassword.IdentificationScreen
import team.aliens.dms_android.feature.auth.comparepassword.ComparePasswordScreen
import team.aliens.dms_android.feature.auth.findid.FindIdScreen
import team.aliens.dms_android.feature.auth.login.LoginScreen
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RootDms(
    route: String,
) {

    val navController = rememberNavController()

    val scaffoldState = rememberScaffoldState()

    Surface(
        modifier = Modifier.background(
            DormTheme.colors.background,
        )
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { hostState ->
                DormToastHost(
                    hostState = hostState,
                )
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = route,
            ) {

                composable(NavigationRoute.Main) {
                    DmsApp(
                        navController = navController,
                        scaffoldState = scaffoldState,
                    )
                }

                signUpNavigation(
                    navController = navController,
                )

                authNavigation(
                    navController = navController,
                )

                myPageNavigation(
                    navController = navController,
                )

                applicationNavigation(
                    navController = navController,
                )

                noticeNavigation(
                    navController = navController,
                )
            }
        }
    }
}
