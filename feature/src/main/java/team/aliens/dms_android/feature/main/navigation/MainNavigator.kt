package team.aliens.dms_android.feature.main.navigation

import team.aliens.dms_android.feature._legacy.util.SelectImageType
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
    fun openEditPasswordNav()
    fun openEditProfileImage(selectImageType: SelectImageType = SelectImageType.NONE)

    fun openUnauthorizedNav()
}
