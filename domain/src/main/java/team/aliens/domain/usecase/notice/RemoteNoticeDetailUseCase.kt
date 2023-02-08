package team.aliens.domain.usecase.notice

import team.aliens.domain.entity.notice.NoticeDetailEntity
import team.aliens.domain.repository.NoticeRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteNoticeDetailUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : UseCase<String, NoticeDetailEntity>() {
    override suspend fun execute(data: String): NoticeDetailEntity =
        noticeRepository.fetchNoticeDetail(data)
}