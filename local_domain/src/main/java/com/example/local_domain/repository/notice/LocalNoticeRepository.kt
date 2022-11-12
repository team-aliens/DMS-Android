package com.example.local_domain.repository.notice

import com.example.local_domain.entity.notice.NoticeDetailLocalEntity
import com.example.local_domain.entity.notice.NoticeListLocalEntity
import java.util.UUID

interface LocalNoticeRepository {

    suspend fun fetchNoticeList(): NoticeListLocalEntity

    suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailLocalEntity
}
