package team.aliens.data._datasource.remote

import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import java.util.*

interface RemoteNoticeDataSource {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotices(
        order: Order,
    ): FetchNoticesOutput

    suspend fun fetchNoticeDetails(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput
}
