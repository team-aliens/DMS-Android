package team.aliens.dms.android.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavigator
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavigator
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.destinations.EditPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.EditProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.EnterEmailScreenDestination
import team.aliens.dms.android.feature.destinations.EnterSchoolVerificationQuestionScreenDestination
import team.aliens.dms.android.feature.destinations.FindIdScreenDestination
import team.aliens.dms.android.feature.destinations.MainDestination
import team.aliens.dms.android.feature.destinations.NoticeDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.NotificationBoxScreenDestination
import team.aliens.dms.android.feature.destinations.NotificationSettingsScreenDestination
import team.aliens.dms.android.feature.destinations.OutingApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.PointHistoryScreenDestination
import team.aliens.dms.android.feature.destinations.RemainsApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.ResetPasswordEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.ResetPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.SetIdScreenDestination
import team.aliens.dms.android.feature.destinations.SetProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.SignInScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomListScreenDestination
import team.aliens.dms.android.feature.destinations.TermsScreenDestination
import team.aliens.dms.android.feature.destinations.VotingScreenDestination
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms.android.feature.outing.navigation.OutingNavGraph
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavGraph
import team.aliens.dms.android.feature.signup.navigation.SignUpNavGraph
import team.aliens.dms.android.feature.voting.navigation.VotingNavGraph
import java.util.UUID

class DmsNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : AuthorizedNavigator, UnauthorizedNavigator {

    override fun openUnauthorizedNav() {
        navController.navigateSingleTop(UnauthorizedNavGraph) {
            popUpTo(AuthorizedNavGraph) {
                inclusive = true
            }
        }
    }

    override fun openSettingsNotification() {
        navController.navigateSingleTop(NotificationSettingsScreenDestination within navGraph)
    }

    override fun openNotificationBox() {
        navController.navigateSingleTop(NotificationBoxScreenDestination within navGraph)
    }

    override fun openStudyRoomList() {
        navController.navigateSingleTop(StudyRoomListScreenDestination within navGraph)
    }

    override fun openRemainsApplication() {
        navController.navigateSingleTop(RemainsApplicationScreenDestination within navGraph) {
            restoreState = true
        }
    }

    override fun openEditProfileImage() {
        navController.navigateSingleTop(EditProfileImageScreenDestination() within AuthorizedNavGraph)
    }

    override fun openPointHistory(pointType: PointType) {
        navController.navigateSingleTop(PointHistoryScreenDestination(pointType) within navGraph)
    }

    override fun openEditPasswordNav() {
        navController.navigateSingleTop(EditPasswordNavGraph)
    }

    override fun openNoticeDetails(noticeId: UUID) {
        navController.navigateSingleTop(NoticeDetailsScreenDestination(noticeId) within navGraph)
    }

    override fun openOutingNav() {
        navController.navigateSingleTop(OutingNavGraph) {
            restoreState = true
        }
    }

    override fun openEditPasswordSetPassword(currentPassword: String) {
        navController.navigateSingleTop(EditPasswordSetPasswordScreenDestination within EditPasswordNavGraph)
    }

    override fun popUpToMain() {
        navController.popBackStack(
            route = MainDestination routedIn AuthorizedNavGraph,
            inclusive = false,
        )
    }

    override fun openSignIn() {
        navController.navigateSingleTop(SignInScreenDestination within navGraph) {
            popUpTo(UnauthorizedNavGraph) { inclusive = true }
            launchSingleTop = true
        }
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openResetPasswordEnterEmailVerificationCode() {
        navController.navigateSingleTop(ResetPasswordEnterEmailVerificationCodeScreenDestination within ResetPasswordNavGraph)
    }

    override fun openResetPasswordSetPassword() {
        navController.navigateSingleTop(ResetPasswordSetPasswordScreenDestination within ResetPasswordNavGraph)
    }

    override fun openAuthorizedNav() {
        navController.navigateSingleTop(AuthorizedNavGraph) {
            popUpTo(UnauthorizedNavGraph) { inclusive = true }
            launchSingleTop = true
        }
    }

    override fun openSignUpNav() {
        navController.navigateSingleTop(SignUpNavGraph)
    }

    override fun openFindId() {
        navController.navigateSingleTop(FindIdScreenDestination within UnauthorizedNavGraph)
    }

    override fun openResetPasswordNav() {
        navController.navigateSingleTop(ResetPasswordNavGraph)
    }

    override fun openEnterSchoolVerificationQuestion() {
        navController.navigateSingleTop(EnterSchoolVerificationQuestionScreenDestination within SignUpNavGraph) {
            this.launchSingleTop = true
        }
    }

    override fun openEnterEmail(clearStack: Boolean) {
        navController.navigateSingleTop(EnterEmailScreenDestination within SignUpNavGraph)
    }

    override fun openSignUpEnterEmailVerificationCode() {
        navController.navigateSingleTop(SignUpEnterEmailVerificationCodeScreenDestination within SignUpNavGraph)
    }

    override fun openSetId() {
        navController.navigateSingleTop(SetIdScreenDestination within SignUpNavGraph)
    }

    override fun openSignUpSetPassword() {
        navController.navigateSingleTop(SignUpSetPasswordScreenDestination within SignUpNavGraph)
    }

    override fun openSetProfileImage() {
        navController.navigateSingleTop(SetProfileImageScreenDestination within SignUpNavGraph)
    }

    override fun openTerms() {
        navController.navigateSingleTop(TermsScreenDestination within SignUpNavGraph)
    }

    override fun popUpToSignUp() {
        navController.popBackStack(
            route = SignUpNavGraph,
            inclusive = true,
        )
    }

    override fun popUpToEnterEmail() {
        navController.navigate(EnterEmailScreenDestination within SignUpNavGraph) {
            popUpTo(EnterEmailScreenDestination routedIn SignUpNavGraph) {
                inclusive = true
            }
        }
    }

    override fun popUpToSetId() {
        navController.navigate(SetIdScreenDestination within SignUpNavGraph) {
            popUpTo(SetIdScreenDestination routedIn SignUpNavGraph) {
                inclusive = true
            }
        }
    }

    override fun popUpToSetPassword() {
        navController.navigate(SignUpSetPasswordScreenDestination within SignUpNavGraph) {
            popUpTo(SignUpSetPasswordScreenDestination routedIn SignUpNavGraph) {
                inclusive = true
            }
        }
    }

    override fun popUpToSetProfileImage() {
        navController.navigate(SignUpSetPasswordScreenDestination within SignUpNavGraph) {
            popUpTo(SignUpSetPasswordScreenDestination routedIn SignUpNavGraph) {
                inclusive = true
            }
        }
    }

    override fun openStudyRoomDetails(
        // TODO: make custom navigation argument
        studyRoomId: UUID,
        studyRoomName: String,
        timeslot: UUID,
        studyRoomApplicationStartTime: String,
        studyRoomApplicationEndTime: String,
    ) {
        navController.navigateSingleTop(
            StudyRoomDetailsScreenDestination(
                studyRoomId = studyRoomId,
                studyRoomName = studyRoomName,
                timeslot = timeslot,
                studyRoomApplicationStartTime = studyRoomApplicationStartTime,
                studyRoomApplicationEndTime = studyRoomApplicationEndTime,
            ) within navGraph,
        )
    }

    override fun openOutingApplication() {
        navController.navigateSingleTop(OutingApplicationScreenDestination within OutingNavGraph)
    }

    override fun openVoting() {
        navController.navigateSingleTop(VotingScreenDestination within VotingNavGraph)
    }
}

private fun NavController.navigateSingleTop(
    direction: Direction,
    navOptionsBuilder: NavOptionsBuilder.() -> Unit = {},
) = this.navigate(direction) {
    navOptionsBuilder()
    launchSingleTop = true
}
