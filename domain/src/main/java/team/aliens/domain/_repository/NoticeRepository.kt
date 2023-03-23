package team.aliens.domain._repository

import team.aliens.domain._model.notice.FetchNoticeDetailOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import java.util.*

interface NoticeRepository {

    suspend fun queryWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun queryNotices(): FetchNoticesOutput

    suspend fun queryNoticeDetail(
        noticeId: UUID,
    ): FetchNoticeDetailOutput
}
