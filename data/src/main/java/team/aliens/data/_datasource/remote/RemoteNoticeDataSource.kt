package team.aliens.data._datasource.remote

import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput

interface RemoteNoticeDataSource {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotices(
        order: Order,
    ): FetchNoticesOutput

    suspend fun fetchNoticeDetails(
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput
}
