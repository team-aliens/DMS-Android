package team.aliens.domain.usecase.notice

import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._repository.NoticeRepository
import javax.inject.Inject

class FetchNoticesUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(
        order: Order,
    ): FetchNoticesOutput {
        return noticeRepository.fetchNotices(order)
    }
}
