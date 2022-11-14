package com.example.local_domain.usecase.notice

import com.example.local_domain.entity.notice.NoticeDetailLocalEntity
import com.example.local_domain.entity.notice.NoticeListLocalEntity
import com.example.local_domain.repository.notice.LocalNoticeRepository
import com.example.local_domain.usecase.UseCase
import java.util.UUID
import javax.inject.Inject

class LocalNoticeDetailUseCase @Inject constructor(
    private val localNoticeRepository: LocalNoticeRepository
): UseCase<UUID, NoticeDetailLocalEntity>() {

    override suspend fun execute(data: UUID): NoticeDetailLocalEntity =
        localNoticeRepository.fetchNoticeDetail(data)
}
