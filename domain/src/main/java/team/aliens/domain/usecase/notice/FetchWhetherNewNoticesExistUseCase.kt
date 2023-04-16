package team.aliens.domain.usecase.notice

import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput
import team.aliens.domain._repository.NoticeRepository
import javax.inject.Inject

class FetchWhetherNewNoticesExistUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(): FetchWhetherNewNoticesExistOutput {
        return noticeRepository.fetchWhetherNewNoticesExist()
    }
}
