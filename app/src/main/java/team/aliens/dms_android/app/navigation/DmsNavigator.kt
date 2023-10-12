package team.aliens.dms_android.app.navigation

import team.aliens.dms_android.app.navigation.authorized.AuthorizedNavigator
import team.aliens.dms_android.app.navigation.unauthorized.UnauthorizedNavigator
import java.util.UUID
import javax.inject.Inject

interface DmsNavigator : AuthorizedNavigator, UnauthorizedNavigator

class DmsNavigatorImpl @Inject constructor() : DmsNavigator {
    override fun openSetPassword() {
        TODO("Not yet implemented")
    }

    override fun openSignIn() {
        TODO("Not yet implemented")
    }

    override fun popBackStack() {
        TODO("Not yet implemented")
    }

    override fun openHome() {
        TODO("Not yet implemented")
    }

    override fun openApplication() {
        TODO("Not yet implemented")
    }

    override fun openAnnouncementList() {
        TODO("Not yet implemented")
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

    override fun openEditPassword() {
        TODO("Not yet implemented")
    }

    override fun switchNavGraphRoot() {
        TODO("Not yet implemented")
    }

    override fun openResetPasswordEnterEmailVerificationCode() {
        TODO("Not yet implemented")
    }

    override fun openResetPasswordSetPassword() {
        TODO("Not yet implemented")
    }

    override fun openMain() {
        TODO("Not yet implemented")
    }

    override fun openSignUp() {
        TODO("Not yet implemented")
    }

    override fun openFindId() {
        TODO("Not yet implemented")
    }

    override fun openResetPassword() {
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
