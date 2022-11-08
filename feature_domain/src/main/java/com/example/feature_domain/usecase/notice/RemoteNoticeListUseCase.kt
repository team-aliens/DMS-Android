package com.example.feature_domain.usecase.notice

import com.example.feature_domain.entity.notice.NoticeListEntity
import com.example.feature_domain.enums.NoticeListSCType
import com.example.feature_domain.repository.NoticeRepository
import com.example.feature_domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
): UseCase<NoticeListSCType, Flow<NoticeListEntity>>() {
    override suspend fun execute(data: NoticeListSCType): Flow<NoticeListEntity> =
        noticeRepository.fetchNoticeList(data)
}
