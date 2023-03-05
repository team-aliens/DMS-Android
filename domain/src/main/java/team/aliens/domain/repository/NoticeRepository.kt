package team.aliens.domain.repository

import team.aliens.domain.entity.notice.NewNoticeBooleanEntity
import team.aliens.domain.entity.notice.NoticeDetailEntity
import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType

interface NoticeRepository {

    suspend fun newNoticeBoolean(): NewNoticeBooleanEntity

    suspend fun fetchNoticeList(order: NoticeListSCType): NoticeListEntity

    suspend fun fetchNoticeDetail(notice_id: String): NoticeDetailEntity
}
