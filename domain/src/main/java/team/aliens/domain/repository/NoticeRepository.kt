package team.aliens.domain.repository

import com.example.domain.entity.notice.NoticeDetailEntity
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType

interface NoticeRepository {

    suspend fun newNoticeBoolean(): Boolean

    suspend fun fetchNoticeList(order: NoticeListSCType): NoticeListEntity

    suspend fun fetchNoticeDetail(notice_id: String): NoticeDetailEntity
}
