package team.aliens.dms.android.feature.notification.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.util.toDateTimeString
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.shared.date.util.now
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class NotificationDetailViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : BaseStateViewModel<NotificationDetailState, NotificationDetailSideEffect>(NotificationDetailState()) {

    internal fun getNotificationDetail(noticeId: UUID) {
        viewModelScope.launch {
            runCatching {
                noticeRepository.fetchNoticeDetails(noticeId = noticeId).getOrThrow()
            }.onSuccess { notice ->
                val noticeUi = NotificationDetailUi(
                    id = notice.id,
                    title = notice.title,
                    content = notice.content,
                    createdAt = notice.createdAt,
                    elapsedCreatedAt = notice.createdAt.toDateTimeString(),
                )
                setState { it.copy(notice = noticeUi) }
            }
        }
    }
}

internal data class NotificationDetailUi(
    val id: UUID,
    val title: String,
    val content: String?,
    val createdAt: LocalDateTime,
    val elapsedCreatedAt: String,
)

internal data class NotificationDetailState(
    val notice: NotificationDetailUi = NotificationDetailUi(
        id = UUID.randomUUID(),
        title = "",
        content = "",
        createdAt = now,
        elapsedCreatedAt = ""
    ),
)

internal sealed interface NotificationDetailSideEffect
