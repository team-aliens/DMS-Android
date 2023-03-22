package team.aliens.domain._repository

import team.aliens.domain._model.notice.QueryNoticeDetailResult
import team.aliens.domain._model.notice.QueryNoticesResult
import team.aliens.domain._model.notice.QueryWhetherNewNoticesExistResult
import java.util.*

interface NoticeRepository {

    suspend fun queryWhetherNewNoticesExist(): QueryWhetherNewNoticesExistResult

    suspend fun queryNotices(): QueryNoticesResult

    suspend fun queryNoticeDetail(
        noticeId: UUID,
    ): QueryNoticeDetailResult
}
