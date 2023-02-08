package team.aliens.domain.usecase.notice

import com.example.domain.entity.notice.NoticeDetailEntity
import com.example.domain.repository.NoticeRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteNoticeDetailUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
): UseCase<String, NoticeDetailEntity>() {
    override suspend fun execute(data: String): NoticeDetailEntity =
        noticeRepository.fetchNoticeDetail(data)
}