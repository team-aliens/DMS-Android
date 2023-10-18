package team.aliens.dms.android.domain._legacy.usecase.notice

import team.aliens.dms.android.domain.model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.dms.android.domain.repository.NoticeRepository
import javax.inject.Inject

class FetchWhetherNewNoticesExistUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(): FetchWhetherNewNoticesExistOutput {
        return noticeRepository.fetchWhetherNewNoticesExist()
    }
}
