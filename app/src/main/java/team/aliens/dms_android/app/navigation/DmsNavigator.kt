package team.aliens.dms_android.app.navigation

import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.NavGraphSpec
import team.aliens.dms_android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms_android.app.navigation.authorized.AuthorizedNavigator
import team.aliens.dms_android.app.navigation.unauthorized.UnauthorizedNavGraph
import team.aliens.dms_android.app.navigation.unauthorized.UnauthorizedNavigator
import team.aliens.dms_android.feature._legacy.util.SelectImageType
import team.aliens.dms_android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms_android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms_android.feature.destinations.EditPasswordSetPasswordScreenDestination
import team.aliens.dms_android.feature.destinations.EditProfileImageScreenDestination
import team.aliens.dms_android.feature.destinations.HomeScreenDestination
import team.aliens.dms_android.feature.destinations.MyPageScreenDestination
import team.aliens.dms_android.feature.destinations.NoticeDetailsScreenDestination
import team.aliens.dms_android.feature.destinations.NotificationBoxScreenDestination
import team.aliens.dms_android.feature.destinations.PointHistoryScreenDestination
import team.aliens.dms_android.feature.destinations.RemainsApplicationScreenDestination
import team.aliens.dms_android.feature.destinations.SignInScreenDestination
import team.aliens.dms_android.feature.destinations.StudyRoomListScreenDestination
import team.aliens.dms_android.feature.editpassword.navigation.EditPasswordNavGraph
import java.util.UUID

class DmsNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavHostController,
) : AuthorizedNavigator,
    UnauthorizedNavigator {
    override fun openEditPasswordSetPasswordNav() {
        navController.navigate(EditPasswordSetPasswordScreenDestination within navGraph)
    }

    override fun openSignIn() {
        navController.navigate(SignInScreenDestination within navGraph)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun openHome() {
        navController.navigate(HomeScreenDestination within navGraph)
    }

    override fun openApplication() {
        navController.navigate(ApplicationScreenDestination within navGraph)
    }

    override fun openAnnouncementList() {
        navController.navigate(AnnouncementListScreenDestination within navGraph)
    }

    override fun openMyPage() {
        navController.navigate(MyPageScreenDestination within navGraph)
    }

    override fun openNoticeDetails(noticeId: UUID) {
        navController.navigate(NoticeDetailsScreenDestination(noticeId = noticeId) within navGraph)
    }

    override fun openStudyRoomList() {
        navController.navigate(StudyRoomListScreenDestination within navGraph)
    }

    override fun openRemainsApplication() {
        navController.navigate(RemainsApplicationScreenDestination within navGraph)
    }

    override fun openNotificationBox() {
        navController.navigate(NotificationBoxScreenDestination within navGraph)
    }

    override fun openPointHistory() {
        navController.navigate(PointHistoryScreenDestination within navGraph)
    }

    override fun openEditPasswordNav() {
        navController.navigate(EditPasswordNavGraph) {
            restoreState = true
            launchSingleTop = true
        }
    }

    override fun openEditProfileImage(selectImageType: SelectImageType) {
        // TODO
        navController.navigate(EditProfileImageScreenDestination(selectImageType) within navGraph)
    }

    override fun openResetPasswordEnterEmailVerificationCode() {
        TODO("Not yet implemented")
    }

    override fun openResetPasswordSetPassword() {
        TODO("Not yet implemented")
    }

    override fun openAuthorizedNav() {
        navController.navigate(AuthorizedNavGraph) {
            popUpTo(UnauthorizedNavGraph) {
                inclusive = true
            }
        }
    }

    override fun openUnauthorizedNav() {
        navController.navigate(UnauthorizedNavGraph) {
            popUpTo(AuthorizedNavGraph) {
                inclusive = true
            }
        }
    }

    override fun openSignUpNav() {
        TODO("Not yet implemented")
    }

    override fun openFindId() {
        TODO("Not yet implemented")
    }

    override fun openResetPasswordNav() {
        TODO("Not yet implemented")
    }

    override fun openEnterSchoolVerificationQuestion() {
        TODO("Not yet implemented")
    }

    override fun openEnterEmail(clearStack: Boolean) {
        TODO("Not yet implemented")
    }

    override fun openSignUpEnterEmailVerificationCode() {
        TODO("Not yet implemented")
    }

    override fun openSetId() {
        TODO("Not yet implemented")
    }

    override fun openSignUpSetPassword() {
        TODO("Not yet implemented")
    }

    override fun openSetProfileImage() {
        TODO("Not yet implemented")
    }

    override fun openTerms() {
        TODO("Not yet implemented")
    }

    override fun openStudyRoomDetails(studyRoomId: UUID, timeslot: UUID) {
        TODO("Not yet implemented")
    }
}
