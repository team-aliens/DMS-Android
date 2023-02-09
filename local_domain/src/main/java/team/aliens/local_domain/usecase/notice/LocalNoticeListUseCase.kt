package team.aliens.local_domain.usecase.notice

import team.aliens.local_domain.entity.notice.NoticeListLocalEntity
import team.aliens.local_domain.repository.notice.LocalNoticeRepository
import team.aliens.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalNoticeListUseCase @Inject constructor(
    private val localNoticeRepository: LocalNoticeRepository,
) : UseCase<Unit, NoticeListLocalEntity>() {

    override suspend fun execute(data: Unit): NoticeListLocalEntity =
        localNoticeRepository.fetchNoticeList()
}
