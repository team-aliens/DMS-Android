package team.aliens.dms_android.domain.usecase.notice

import team.aliens.dms_android.domain.model.notice.FetchNoticeDetailsInput
import team.aliens.dms_android.domain.model.notice.FetchNoticeDetailsOutput
import team.aliens.dms_android.domain.repository.NoticeRepository
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
