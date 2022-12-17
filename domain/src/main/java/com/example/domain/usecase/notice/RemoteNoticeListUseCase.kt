package com.example.domain.usecase.notice

import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType
import com.example.domain.repository.NoticeRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
): UseCase<NoticeListSCType, Flow<NoticeListEntity>>() {
    override suspend fun execute(data: NoticeListSCType): Flow<NoticeListEntity> =
        noticeRepository.fetchNoticeList(data)
}
