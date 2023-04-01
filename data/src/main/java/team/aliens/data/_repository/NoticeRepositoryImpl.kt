package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteNoticeDataSource
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.domain._repository.NoticeRepository
import java.util.*
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val remoteNoticeDataSource: RemoteNoticeDataSource,
) : NoticeRepository {

    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput {
        return remoteNoticeDataSource.fetchWhetherNewNoticesExist()
    }

    override suspend fun fetchNotices(
        order: Order,
    ): FetchNoticesOutput {
        return remoteNoticeDataSource.fetchNotices(
            order = order,
        )
    }

    override suspend fun fetchNoticeDetails(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput {
        return remoteNoticeDataSource.fetchNoticeDetails(
            noticeId = noticeId,
        )
    }
}
