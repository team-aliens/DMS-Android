package team.aliens.domain.usecase.notice

import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._repository.NoticeRepository
import java.util.UUID
import javax.inject.Inject

class FetchNoticeDetailsUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(
        noticeId: UUID,
    ): FetchNoticeDetailsOutput {
        return noticeRepository.fetchNoticeDetails(noticeId)
    }
}
