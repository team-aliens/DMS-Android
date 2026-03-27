package team.aliens.dms.android.feature.notice.viewmodel

import androidx.compose.runtime.Immutable
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
internal class NoticeDetailViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
) : BaseStateViewModel<NoticeDetailState, NoticeDetailSideEffect>(NoticeDetailState()) {

    internal fun getNotificationDetail(noticeId: UUID) {
        viewModelScope.launch {
            noticeRepository.fetchNoticeDetails(noticeId = noticeId).onSuccess { notice ->
                val noticeUi = NoticeDetailUi(
                    id = notice.id,
                    title = notice.title,
                    content = notice.content,
                    createdAt = notice.createdAt,
                    elapsedCreatedAt = notice.createdAt.toDateTimeString(),
                )
                setState { it.copy(notice = noticeUi) }
            }.onFailure {
                sendEffect(NoticeDetailSideEffect.FailNoticeDetail)
            }
        }
    }
}

internal data class NoticeDetailUi(
    val id: UUID,
    val title: String,
    val content: String?,
    val createdAt: LocalDateTime,
    val elapsedCreatedAt: String,
)

@Immutable
internal data class NoticeDetailState(
    val notice: NoticeDetailUi = NoticeDetailUi(
        id = UUID.randomUUID(),
        title = "",
        content = "",
        createdAt = now,
        elapsedCreatedAt = ""
    ),
)

internal sealed interface NoticeDetailSideEffect {
    object FailNoticeDetail : NoticeDetailSideEffect
}
