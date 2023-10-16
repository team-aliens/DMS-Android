package team.aliens.dms.android.data._legacy.datasource.local

import team.aliens.dms.android.domain.model.notice.Notice
import java.util.UUID

interface LocalNoticeDataSource {

    fun fetchNotice(
        noticeId: UUID,
    ): Notice

    fun fetchNotices(): List<Notice>

    fun saveNotice(
        notice: Notice,
    )

    fun saveNotices(
        vararg notices: Notice,
    )
}
