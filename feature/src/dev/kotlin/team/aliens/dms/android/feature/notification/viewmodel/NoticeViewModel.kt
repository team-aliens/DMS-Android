package team.aliens.dms.android.feature.notification.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import team.aliens.dms.android.shared.date.toElapsedText
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.model.Order
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class NoticeViewModel @Inject constructor(
    private val noticeRepository: NoticeRepository,
    private val notificationRepository: NotificationRepository,
) : BaseStateViewModel<NotificationState, NoticesSideEffect>(NotificationState()) {

    init {
        getNotices()
        fetchNotifications()
    }

    private fun getNotices() {
        viewModelScope.launch {
            runCatching {
                noticeRepository.fetchNotices(order = if (uiState.value.isRecent) Order.OLD else Order.NEW)
            }
            .onSuccess { notices ->
                val uiNotices = notices.map { notice ->
                    NoticeUi(
                        id = notice.id,
                        title = notice.title,
                        content = notice.content,
                        createdAt = notice.createdAt,
                        elapsedText = notice.createdAt.toElapsedText(now),
                    )
                }
                setState { it.copy(notices = uiNotices) }
            }.onFailure {

            }
        }
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.fetchNotifications()
                .onSuccess { notifications ->
                    val notificationsUi = notifications.map { notification -> NoticeUi(
                        id = notification.id,
                        title = notification.title,
                        content = notification.content,
                        createdAt = notification.createdAt,
                        elapsedText = notification.createdAt.toElapsedText(now),
                    ) }
                    setState { it.copy(notifications = notificationsUi) }
                }
        }
    }
}

internal data class NoticeUi(
    val id: UUID,
    val title: String,
    val content: String?,
    val createdAt: LocalDateTime,
    val elapsedText: String,
)

internal data class NotificationState(
    val isRecent: Boolean = false,
    val notices: List<NoticeUi> = emptyList(),
    val notifications: List<NoticeUi> = emptyList(),
)

internal sealed interface NoticesSideEffect
