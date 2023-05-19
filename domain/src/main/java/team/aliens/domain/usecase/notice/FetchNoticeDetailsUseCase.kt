package team.aliens.domain.usecase.notice

import team.aliens.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.domain.repository.NoticeRepository
import javax.inject.Inject

class FetchNoticeDetailsUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(
        fetchNoticeDetailsInput: FetchNoticeDetailsInput,
    ): FetchNoticeDetailsOutput {
        return noticeRepository.fetchNoticeDetails(
            input = fetchNoticeDetailsInput,
        )
    }
}
