package team.aliens.local_domain.usecase.notice

import team.aliens.local_domain.entity.notice.NoticeDetailLocalEntity
import team.aliens.local_domain.repository.notice.LocalNoticeRepository
import team.aliens.local_domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class LocalNoticeDetailUseCase @Inject constructor(
    private val localNoticeRepository: LocalNoticeRepository,
) : UseCase<UUID, NoticeDetailLocalEntity>() {

    override suspend fun execute(data: UUID): NoticeDetailLocalEntity =
        localNoticeRepository.fetchNoticeDetail(data)
}
