package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteNoticeDataSource
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import java.util.*

class RemoteNoticeDataSourceImpl : RemoteNoticeDataSource {

    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNotices(): FetchNoticesOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNoticeDetails(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput {
        TODO("Not yet implemented")
    }
}
