package team.aliens.domain._repository

import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.domain._model.notice.Notice
import java.util.*

interface NoticeRepository {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotice(
        noticeId: UUID,
    ): Notice

    suspend fun fetchNoticeDetails(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput

    suspend fun fetchNotices(
        order: Order,
    ): FetchNoticesOutput

    suspend fun saveNotice(
        notice: Notice,
    )

    suspend fun saveNotices(
        vararg notices: Notice,
    )
}
