package team.aliens.data._datasource.remote

import team.aliens.domain._model.notice.FetchNoticeDetailOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import java.util.*

interface RemoteNoticeDataSource {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotices(): FetchNoticesOutput

    suspend fun fetchNoticeDetail(
        noticeId: UUID,
    ): FetchNoticeDetailOutput
}
