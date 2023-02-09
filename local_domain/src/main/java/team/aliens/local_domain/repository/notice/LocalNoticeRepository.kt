package team.aliens.local_domain.repository.notice

import team.aliens.local_domain.entity.notice.NoticeDetailLocalEntity
import team.aliens.local_domain.entity.notice.NoticeListLocalEntity
import java.util.*

interface LocalNoticeRepository {

    suspend fun fetchNoticeList(): NoticeListLocalEntity

    suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailLocalEntity
}
