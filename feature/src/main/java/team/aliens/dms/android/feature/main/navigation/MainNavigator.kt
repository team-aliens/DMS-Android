package team.aliens.dms.android.feature.main.navigation

import java.util.UUID

interface MainNavigator {

    fun openNotificationBox()

    fun openUnauthorizedNav()

    fun openStudyRoomList()

    fun openRemainsApplication()

    fun openPointHistory()

    fun openEditPassword()

    fun openNoticeDetails(noticeId: UUID)
}
