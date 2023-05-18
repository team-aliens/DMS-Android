package team.aliens.domain.usecase.notice

import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.FetchNoticesOutput
import team.aliens.domain.repository.NoticeRepository
import javax.inject.Inject

class FetchNoticesUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(
        fetchNoticesInput: FetchNoticesInput,
    ): FetchNoticesOutput {
        return noticeRepository.fetchNotices(
            input = fetchNoticesInput,
        )
    }
}
