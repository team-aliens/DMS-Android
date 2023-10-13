package team.aliens.dms.android.domain.repository

import team.aliens.dms.android.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.dms.android.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.dms.android.domain.model.notice.FetchNoticesInput
import team.aliens.dms.android.domain.model.notice.FetchNoticesOutput
import team.aliens.dms.android.domain.model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.dms.android.domain.model.notice.Notice
import java.util.UUID

interface NoticeRepository {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotice(
        noticeId: UUID,
    ): Notice

    suspend fun fetchNoticeDetails(
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput

    suspend fun fetchNotices(
        input: FetchNoticesInput,
    ): FetchNoticesOutput

    suspend fun saveNotice(
        notice: Notice,
    )

    suspend fun saveNotices(
        vararg notices: Notice,
    )
}
