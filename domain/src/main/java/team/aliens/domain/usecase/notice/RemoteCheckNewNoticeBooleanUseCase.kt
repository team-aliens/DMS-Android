package team.aliens.domain.usecase.notice

import team.aliens.domain.repository.NoticeRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteCheckNewNoticeBooleanUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : UseCase<Unit, Boolean>() {
    override suspend fun execute(data: Unit): Boolean = noticeRepository.newNoticeBoolean()
}