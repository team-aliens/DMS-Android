package com.example.domain.usecase.notice

import com.example.domain.repository.NoticeRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckNewNoticeBooleanUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
): UseCase<Unit, Boolean>() {
    override suspend fun execute(data: Unit): Boolean =
        noticeRepository.newNoticeBoolean()
}