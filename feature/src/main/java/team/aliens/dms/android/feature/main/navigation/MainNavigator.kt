package team.aliens.dms.android.feature.main.navigation

import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.data.point.model.PointType
import java.util.UUID

interface MainNavigator {

    fun openUnauthorizedNav()

    fun openSettingsNotification()

    fun openNotificationBox()

    fun openStudyRoomList()

    fun openRemainsApplication()

    fun openEditProfileImage()

    fun openPointHistory(pointType: PointType)

    fun openEditPasswordNav()

    fun openNoticeDetails(noticeId: UUID)

    fun openOutingNav()

    fun openVolunteer(webViewUrl: String, accessToken: String, refreshToken: String,)
}
