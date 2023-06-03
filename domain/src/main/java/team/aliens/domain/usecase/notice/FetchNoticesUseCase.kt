package team.aliens.domain.usecase.notice

import team.aliens.domain.model.notice.FetchNoticesInput
import team.aliens.domain.model.notice.Notice
import team.aliens.domain.model.notice.toModel
import team.aliens.domain.repository.NoticeRepository
import javax.inject.Inject

class FetchNoticesUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(
        fetchNoticesInput: FetchNoticesInput,
    ): List<Notice> {
        return noticeRepository.fetchNotices(
            input = fetchNoticesInput,
        ).toModel()
    }
}
