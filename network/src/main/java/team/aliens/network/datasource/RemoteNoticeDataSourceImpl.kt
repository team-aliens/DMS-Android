package team.aliens.network.datasource

import team.aliens.data.datasource.remote.RemoteNoticeDataSource
import team.aliens.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.FetchNoticesOutput
import team.aliens.domain.model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.network.model.notice.toDomain
import team.aliens.network.apiservice.NoticeApiService
import team.aliens.network.util.sendHttpRequest
import javax.inject.Inject

class RemoteNoticeDataSourceImpl @Inject constructor(
    private val noticeApiService: NoticeApiService,
) : RemoteNoticeDataSource {

    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput {
        return sendHttpRequest {
            noticeApiService.fetchWhetherNewNoticesExist()
        }.toDomain()
    }

    override suspend fun fetchNotices(
        input: FetchNoticesInput,
    ): FetchNoticesOutput {
        return sendHttpRequest {
            noticeApiService.fetchNotices(
                order = input.order.name,
            )
        }.toDomain()
    }

    override suspend fun fetchNoticeDetails(
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput {
        return sendHttpRequest {
            noticeApiService.fetchNoticeDetails(
                noticeId = input.noticeId,
            )
        }.toDomain()
    }
}
