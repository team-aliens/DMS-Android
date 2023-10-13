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
import team.aliens.dms_android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms_android.feature.destinations.EditPasswordSetPasswordScreenDestination
import team.aliens.dms_android.feature.destinations.HomeScreenDestination
import team.aliens.dms_android.feature.destinations.SignInScreenDestination
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
        navCon
    }

    override fun openMyPage() {
        TODO("Not yet implemented")
    }

    override fun openNoticeDetails(noticeId: UUID) {
        TODO("Not yet implemented")
    }

    override fun openStudyRoomList() {
        TODO("Not yet implemented")
    }

    override fun openRemainsApplication() {
        TODO("Not yet implemented")
    }

    override fun openNotificationBox() {
        TODO("Not yet implemented")
    }

    override fun openPointHistory() {
        TODO("Not yet implemented")
    }

    override fun openEditPasswordNav() {
        TODO("Not yet implemented")
    }

    override fun openEditProfileImage() {
        TODO("Not yet implemented")
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
