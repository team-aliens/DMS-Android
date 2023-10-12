package team.aliens.dms_android.feature.main.navigation

import java.util.UUID

interface MainNavigator {
    fun openHome()
    fun openApplication()
    fun openAnnouncementList()
    fun openMyPage()
    fun openNavigateToNoticeDetails(noticeId: UUID)

    fun switchNavGraphRoot()
}
