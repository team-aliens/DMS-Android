package com.example.domain.repository

import com.example.domain.entity.notice.NewNoticeBooleanEntity
import com.example.domain.entity.notice.NoticeDetailEntity
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NoticeRepository {

    suspend fun newNoticeBoolean(): Flow<NewNoticeBooleanEntity>

    suspend fun fetchNoticeList(order: NoticeListSCType): Flow<NoticeListEntity>

    suspend fun fetchNoticeDetail(notice_id: UUID): Flow<NoticeDetailEntity>
}
