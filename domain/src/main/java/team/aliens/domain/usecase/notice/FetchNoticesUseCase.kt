package team.aliens.domain.usecase.notice

import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._repository.NoticeRepository
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
