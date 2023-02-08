package team.aliens.domain.usecase.notice

import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType
import com.example.domain.repository.NoticeRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class
RemoteNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) : UseCase<NoticeListSCType, NoticeListEntity>() {
    override suspend fun execute(data: NoticeListSCType): NoticeListEntity =
        noticeRepository.fetchNoticeList(data)
}
