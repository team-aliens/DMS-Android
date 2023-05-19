package team.aliens.data.datasource.remote

import team.aliens.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.FetchNoticesOutput
import team.aliens.domain.model.notice.FetchWhetherNewNoticesExistOutput

interface RemoteNoticeDataSource {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotices(
        input: FetchNoticesInput,
    ): FetchNoticesOutput

    suspend fun fetchNoticeDetails(
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput
}
