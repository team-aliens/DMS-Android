package team.aliens.data._repository

import java.util.UUID
import javax.inject.Inject
import team.aliens.data._datasource.local.LocalNoticeDataSource
import team.aliens.data._datasource.remote.RemoteNoticeDataSource
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.domain._model.notice.Notice
import team.aliens.domain._model.notice.toModel
import team.aliens.domain._model.notice.toTypedArray
import team.aliens.domain._repository.NoticeRepository

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
                this.fetchNotices(
                    input = FetchNoticesInput(
                        order = Order.NEW,
                    ),
                )

        } catch (e: Exception) {
            this.fetchNotices(
                input = FetchNoticesInput(
                    order = Order.NEW,
                ),
            )
        }

        return localNoticeDataSource.fetchNotice(
            noticeId = noticeId,
        )
    }

    override suspend fun fetchNotices(
        input: FetchNoticesInput,
    ): FetchNoticesOutput {
        return remoteNoticeDataSource.fetchNotices(
            input = input,
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
        input: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput {
        return remoteNoticeDataSource.fetchNoticeDetails(
            input = input,
        ).also {
            this.saveNotice(
                notice = it.toModel(),
            )
        }
    }
}
