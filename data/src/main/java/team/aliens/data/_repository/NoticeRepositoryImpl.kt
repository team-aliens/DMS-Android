package team.aliens.data._repository

import team.aliens.data._datasource.local.LocalNoticeDataSource
import team.aliens.data._datasource.remote.RemoteNoticeDataSource
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.*
import team.aliens.domain._repository.NoticeRepository
import java.util.*
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val localNoticeDataSource: LocalNoticeDataSource,
    private val remoteNoticeDataSource: RemoteNoticeDataSource,
) : NoticeRepository {

    override suspend fun fetchWhetherNewNoticesExist(): FetchWhetherNewNoticesExistOutput {
        return remoteNoticeDataSource.fetchWhetherNewNoticesExist()
    }

    override suspend fun fetchNotice(
        noticeId: UUID,
    ): Notice {

        try {

            val fetchedNotice = localNoticeDataSource.fetchNotice(
                noticeId = noticeId,
            )

            if (fetchedNotice.content == null)
                this.fetchNotice(
                    noticeId = noticeId,
                )

        } catch (e: Exception) {
            this.fetchNotices(
                order = Order.NEW,
            )
        }

        return localNoticeDataSource.fetchNotice(
            noticeId = noticeId,
        )
    }

    override suspend fun fetchNotices(
        order: Order,
    ): FetchNoticesOutput {
        return remoteNoticeDataSource.fetchNotices(
            order = order,
        ).also {
            this.saveNotices(
                notices = it.notices.toTypedArray(),
            )
        }
    }

    override suspend fun saveNotice(
        notice: Notice,
    ) {
        return localNoticeDataSource.saveNotice(
            notice = notice,
        )
    }

    override suspend fun saveNotices(
        vararg notices: Notice,
    ) {
        return localNoticeDataSource.saveNotices(
            notices = notices,
        )
    }

    override suspend fun fetchNoticeDetails(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput {
        return remoteNoticeDataSource.fetchNoticeDetails(
            noticeId = noticeId,
        ).also {
            this.saveNotice(
                notice = it.toModel(),
            )
        }
    }
}
