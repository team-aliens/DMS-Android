package team.aliens.dms.android.feature.main.navigation

import java.util.UUID

interface MainNavigator {

    fun openNotificationBox()

    fun openUnauthorizedNav()

    fun openStudyRoomList()

    fun openRemainsApplication()

    fun openEditProfileImage()

    fun openPointHistory()

    fun openEditPasswordNav()

    fun openNoticeDetails(noticeId: UUID)

    fun openOutingNav()
}
