package team.aliens.dms.android.feature.notification.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.model.NotificationTopic
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
    private val notificationRepository: NotificationRepository,
) : BaseStateViewModel<NotificationState, NoticesSideEffect>(NotificationState()) {

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.fetchNotifications()
                .onSuccess { notifications ->
                    val notificationsUi = notifications.map { notification -> NotificationUi(
                        id = notification.id,
                        title = notification.title,
                        content = notification.content,
                        createdAt = notification.createdAt,
                        isRead = notification.isRead,
                        linkId = notification.linkId,
                        topic = notification.topic,
                        elapsedText = notification.createdAt.toElapsedText(now),
                    ) }
                    val notification = notificationsUi.filter { it.topic == NotificationTopic.POINT }
                    val notices = notificationsUi.filter { it.topic == NotificationTopic.NOTICE }

                    setState { it.copy(
                        notifications = notification,
                        notices = notices
                    ) }
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

internal data class NotificationUi(
    val id: UUID,
    val topic: NotificationTopic,
    val linkId: UUID,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
    val elapsedText: String,
)

internal data class NotificationState(
    val isRecent: Boolean = false,
    val notices: List<NotificationUi> = emptyList(),
    val notifications: List<NotificationUi> = emptyList(),
)

internal sealed interface NoticesSideEffect
