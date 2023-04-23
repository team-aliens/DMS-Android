package team.aliens.domain.usecase.notice

import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput
import team.aliens.domain._repository.NoticeRepository
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
