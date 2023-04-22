package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteNoticeDataSource
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.remote.model.notice.toDomain
import team.aliens.remote.apiservice.NoticeService
import team.aliens.remote.util.sendHttpRequest
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
        input: FetchNoticesInput,
    ): FetchNoticesOutput {
        return sendHttpRequest {
            noticeService.fetchNotices(
                order = input.order.name,
            )
        }.toDomain()
    }

    override suspend fun fetchNoticeDetails(
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput {
        return sendHttpRequest {
            noticeService.fetchNoticeDetails(
                noticeId = input.noticeId,
            )
        }.toDomain()
    }
}
