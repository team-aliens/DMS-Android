package team.aliens.dms.android.feature.notification.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.shared.date.toElapsedText
import team.aliens.dms.android.shared.date.util.now
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val noticeRepository: NoticeRepository,
) : BaseStateViewModel<NotificationState, NotificationSideEffect>(
    NotificationState(),
) {

    init {
        fetchNotifications()
        fetchNotices()
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.fetchNotifications()
                .onSuccess { notifications ->
                    val notificationsUi = notifications
                        .filter { notification -> notification.topic != NotificationTopic.NOTICE }
                        .map { notification ->
                            NotificationUi(
                                id = notification.id,
                                pointDetailTopic = notification.pointDetailTopic,
                                title = notification.title,
                                content = notification.content,
                                isRead = notification.isRead,
                                elapsedText = notification.createdAt.toElapsedText(now),
                            )
                        }

                    setState { it.copy(notifications = notificationsUi) }
                }
                .onFailure {
                    sendEffect(NotificationSideEffect.FailFetchNotification)
                }
        }
    }

    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            noticeRepository.fetchNotices()
                .onSuccess { notices ->
                    val noticesUi = notices.map { notice ->
                        NoticeUi(
                            id = notice.id,
                            title = notice.title,
                            elapsedText = notice.createdAt.toElapsedText(now),
                        )
                    }

                    setState { it.copy(notices = noticesUi) }
                }
                .onFailure {
                    sendEffect(NotificationSideEffect.FailFetchNotice)
                }
        }
    }

    internal fun updateNotificationReadStatus(notificationId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.updateNotificationReadStatus(notificationId).fold(
                onSuccess = {
                    fetchNotifications()
                },
                onFailure = {
                    sendEffect(NotificationSideEffect.FailUpdateNotification)
                },
            )
        }
    }
}

@Immutable
internal data class NotificationUi(
    val id: UUID,
    val pointDetailTopic: PointType,
    val title: String,
    val content: String,
    val isRead: Boolean,
    val elapsedText: String,
)

@Immutable
internal data class NoticeUi(
    val id: UUID,
    val title: String,
    val elapsedText: String,
)

@Immutable
internal data class NotificationState(
    val isRecent: Boolean = false,
    val notices: List<NoticeUi> = emptyList(),
    val notifications: List<NotificationUi> = emptyList(),
)

internal sealed interface NotificationSideEffect {
    data object FailFetchNotification : NotificationSideEffect
    data object FailFetchNotice : NotificationSideEffect
    data object FailUpdateNotification : NotificationSideEffect
}
