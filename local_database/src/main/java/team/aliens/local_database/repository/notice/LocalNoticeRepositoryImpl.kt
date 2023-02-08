package team.aliens.local_database.repository.notice

import team.aliens.local_database.datasource.declaration.LocalNoticeDataSource
import team.aliens.local_domain.entity.notice.NoticeDetailLocalEntity
import team.aliens.local_domain.entity.notice.NoticeListLocalEntity
import team.aliens.local_domain.repository.notice.LocalNoticeRepository
import java.util.*
import javax.inject.Inject

class LocalNoticeRepositoryImpl @Inject constructor(
    private val localNoticeDataSource: LocalNoticeDataSource,
) : LocalNoticeRepository {
    override suspend fun fetchNoticeList(): NoticeListLocalEntity =
        localNoticeDataSource.fetchNoticeList("").toEntity()

    override suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailLocalEntity =
        localNoticeDataSource.fetchNoticeDetail(noticeId).toEntity()
}

