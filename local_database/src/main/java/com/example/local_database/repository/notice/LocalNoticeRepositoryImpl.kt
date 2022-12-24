package com.example.local_database.repository.notice

import com.example.local_database.datasource.declaration.LocalNoticeDataSource
import com.example.local_database.entity.notice.toEntity
import com.example.local_domain.entity.notice.NoticeDetailLocalEntity
import com.example.local_domain.entity.notice.NoticeListLocalEntity
import com.example.local_domain.repository.notice.LocalNoticeRepository
import java.util.UUID
import javax.inject.Inject

class LocalNoticeRepositoryImpl @Inject constructor(
    private val localNoticeDataSource: LocalNoticeDataSource
): LocalNoticeRepository {
    override suspend fun fetchNoticeList(): NoticeListLocalEntity =
        localNoticeDataSource.fetchNoticeList("").toEntity()

    override suspend fun fetchNoticeDetail(noticeId: UUID): NoticeDetailLocalEntity =
        localNoticeDataSource.fetchNoticeDetail(noticeId).toEntity()
}

