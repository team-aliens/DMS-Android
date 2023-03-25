package team.aliens.domain._repository

import team.aliens.domain._model.notice.FetchNoticeDetailOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import java.util.*

interface NoticeRepository {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotices(): FetchNoticesOutput

    suspend fun fetchNoticeDetail(
        noticeId: UUID,
    ): FetchNoticeDetailOutput
}
