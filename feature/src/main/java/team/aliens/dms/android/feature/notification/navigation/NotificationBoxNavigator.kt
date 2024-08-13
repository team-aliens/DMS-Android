package team.aliens.dms.android.feature.notification.navigation

import java.util.UUID

interface NotificationBoxNavigator {
    fun navigateUp()

    fun openNoticeDetails(noticeId: UUID)
}
