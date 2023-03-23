package team.aliens.data._repository

import team.aliens.domain._model.notice.FetchNoticeDetailOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.domain._repository.NoticeRepository
import java.util.*

class NoticeRepositoryImpl(
    // private val remoteNoticeDataSource: RemoteNoticeDataSource,
) : NoticeRepository {

    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNotices(): FetchNoticesOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchNoticeDetail(
        noticeId: UUID,
    ): FetchNoticeDetailOutput {
        TODO("Not yet implemented")
    }
}
