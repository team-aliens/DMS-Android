package team.aliens.dms.android.feature.main.announcement.navigation

import java.util.UUID

internal interface AnnouncementNavigator {

    fun openNoticeDetails(noticeId: UUID)
}
