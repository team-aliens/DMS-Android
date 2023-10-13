package team.aliens.dms.android.data.datasource.remote

import team.aliens.dms.android.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.dms.android.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.dms.android.domain.model.notice.FetchNoticesInput
import team.aliens.dms.android.domain.model.notice.FetchNoticesOutput
import team.aliens.dms.android.domain.model.notice.FetchWhetherNewNoticesExistOutput

interface RemoteNoticeDataSource {

    suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput

    suspend fun fetchNotices(
        input: FetchNoticesInput,
    ): FetchNoticesOutput

    suspend fun fetchNoticeDetails(
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput
}
