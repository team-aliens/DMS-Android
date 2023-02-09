package team.aliens.domain.usecase.notice

import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType
import team.aliens.domain.repository.NoticeRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : UseCase<NoticeListSCType, NoticeListEntity>() {
    override suspend fun execute(data: NoticeListSCType): NoticeListEntity =
        noticeRepository.fetchNoticeList(data)
}
