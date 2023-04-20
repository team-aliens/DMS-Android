package team.aliens.domain._repository

import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.domain._model.notice.Notice
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
