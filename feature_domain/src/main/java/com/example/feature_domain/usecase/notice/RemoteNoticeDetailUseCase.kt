package com.example.feature_domain.usecase.notice

import com.example.feature_domain.entity.notice.NoticeDetailEntity
import com.example.feature_domain.repository.NoticeRepository
import com.example.feature_domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class RemoteNoticeDetailUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
): UseCase<UUID, Flow<NoticeDetailEntity>>() {
    override suspend fun execute(data: UUID): Flow<NoticeDetailEntity> =
        noticeRepository.fetchNoticeDetail(data)
}