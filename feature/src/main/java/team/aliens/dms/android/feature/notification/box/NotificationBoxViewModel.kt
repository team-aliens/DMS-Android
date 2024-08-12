package team.aliens.dms.android.feature.notification.box

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class NotificationBoxViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
) : BaseMviViewModel<NotificationBoxUiState, NotificationBoxIntent, NotificationBoxSideEffect>(
    initialState = NotificationBoxUiState.initial()
) {

    init {
        fetchNotifications()
    }

    override fun processIntent(intent: NotificationBoxIntent) {
        when(intent) {
            is NotificationBoxIntent.DetailNotification -> detailNotification(intent.notification)
        }
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                notificationRepository.fetchNotifications()
            }.onSuccess { notifications ->
                reduce(newState = stateFlow.value.copy(notifications = notifications))
            }.onFailure {
                postSideEffect(NotificationBoxSideEffect.CurrentNotificationsNotFound)
            }
        }
    }

    private fun detailNotification(notification: Notification) {
        when(notification.topic) {
            NotificationTopic.NOTICE -> {
                postSideEffect(NotificationBoxSideEffect.MoveToDetail(notification.linkId))
            }
            else -> {
                // 처리 해야 할 작업 없음
            }
        }
    }
}

internal data class NotificationBoxUiState(
    val notifications: List<Notification>,
    val topic: NotificationTopic,
) : UiState() {
    companion object {
        fun initial(): NotificationBoxUiState {
            return NotificationBoxUiState(
                notifications = listOf(),
                topic = NotificationTopic.POINT,
            )
        }
    }
}

internal sealed class NotificationBoxIntent : Intent() {
    class DetailNotification(val notification: Notification): NotificationBoxIntent()
}

internal sealed class NotificationBoxSideEffect : SideEffect() {
    data object CurrentNotificationsNotFound: NotificationBoxSideEffect()
    data class MoveToDetail(val detailId: UUID): NotificationBoxSideEffect()
}
