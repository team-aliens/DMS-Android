package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteNoticeDataSource
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.remote.model.notice.toDomain
import team.aliens.remote.service.NoticeService
import team.aliens.remote.util.sendHttpRequest
import java.util.*
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeService: NoticeService,
) : RemoteNoticeDataSource {

    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput {
        return sendHttpRequest {
            noticeService.fetchWhetherNewNoticesExist()
        }.toDomain()
    }

    override suspend fun fetchNotices(
        order: Order,
    ): FetchNoticesOutput {
        return sendHttpRequest {
            noticeService.fetchNotices(
                order = order.name,
            )
        }.toDomain()
    }

    override suspend fun fetchNoticeDetails(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput {
        return sendHttpRequest {
            noticeService.fetchNoticeDetails(
                noticeId = noticeId,
            )
        }.toDomain()
    }
}
