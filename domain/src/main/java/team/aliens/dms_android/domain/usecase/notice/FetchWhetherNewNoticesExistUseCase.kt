package team.aliens.dms_android.domain.usecase.notice

import team.aliens.dms_android.domain.model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.dms_android.domain.repository.NoticeRepository
import javax.inject.Inject

class FetchWhetherNewNoticesExistUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(): FetchWhetherNewNoticesExistOutput {
        return noticeRepository.fetchWhetherNewNoticesExist()
    }
}
