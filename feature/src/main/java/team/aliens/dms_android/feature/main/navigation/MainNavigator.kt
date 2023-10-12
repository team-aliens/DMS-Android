package team.aliens.dms_android.feature.main.navigation

import java.util.UUID

interface MainNavigator {
    fun openHome()
    fun openApplication()
    fun openAnnouncementList()
    fun openMyPage()
    fun openNoticeDetails(noticeId: UUID)
    fun openStudyRoomList()
    fun openRemainsApplication()
    fun openNotificationBox()
    fun openPointHistory()
    fun openEditPassword()
    fun openEditProfileImage()

    fun switchNavGraphRoot()
}
