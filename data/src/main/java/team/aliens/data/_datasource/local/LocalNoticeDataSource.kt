package team.aliens.data._datasource.local

import team.aliens.domain._model.notice.Notice
import java.util.*

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
