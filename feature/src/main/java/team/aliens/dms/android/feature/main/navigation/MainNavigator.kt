package team.aliens.dms.android.feature.main.navigation

import team.aliens.dms.android.data.point.model.PointType
import java.util.UUID

interface MainNavigator {

    fun openNotificationBox()

    fun openUnauthorizedNav()

    fun openStudyRoomList()

    fun openRemainsApplication()

    fun openEditProfileImage()

    fun openPointHistory(pointType: PointType)

    fun openEditPasswordNav()

    fun openNoticeDetails(noticeId: UUID)
}
