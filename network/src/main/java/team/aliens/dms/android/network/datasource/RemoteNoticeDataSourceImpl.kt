package team.aliens.dms.android.network.datasource

import team.aliens.dms_android.data.datasource.remote.RemoteNoticeDataSource
import team.aliens.dms.android.network.apiservice.NoticeApiService
import team.aliens.dms.android.network.model.notice.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms_android.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.dms_android.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.dms_android.domain.model.notice.FetchNoticesInput
import team.aliens.dms_android.domain.model.notice.FetchNoticesOutput
import team.aliens.dms_android.domain.model.notice.FetchWhetherNewNoticesExistOutput
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
