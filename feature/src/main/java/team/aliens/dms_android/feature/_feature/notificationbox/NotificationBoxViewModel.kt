package team.aliens.dms_android.feature._feature.notificationbox

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.base.BaseMviViewModel
import team.aliens.dms_android.feature.base.MviIntent
import team.aliens.dms_android.feature.base.MviSideEffect
import team.aliens.dms_android.feature.base.MviState
import team.aliens.dms_android.domain.model.notification.Notification
import team.aliens.dms_android.domain.usecase.notification.FetchNotificationsUseCase
import javax.inject.Inject

@HiltViewModel
internal class NotificationBoxViewModel @Inject constructor(
    private val fetchNotificationsUseCase: FetchNotificationsUseCase,
) : BaseMviViewModel<NotificationBoxIntent, NotificationBoxState, NotificationBoxSideEffect>(
    initialState = NotificationBoxState.initial(),
) {
    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchNotificationsUseCase().onSuccess { fetchedNotifications ->
                val newNotificationsAndPriorNotifications =
                    fetchedNotifications.extractNewNotificationsAndPriorNotifications()
                reduce(
                    newState = stateFlow.value.copy(
                        newNotifications = newNotificationsAndPriorNotifications.first,
                        priorNotifications = newNotificationsAndPriorNotifications.second,
                    ),
                )
            }.onFailure {
                postSideEffect(NotificationBoxSideEffect.FetchingNotificationsFailed)
            }
        }
    }

    /**
     * @return [Pair.first] new notifications
     * @return [Pair.second] prior notifications
     */
    private fun List<Notification>.extractNewNotificationsAndPriorNotifications(): Pair<List<Notification>, List<Notification>> {
        val newNotifications = this.filter { it.read }
        val priorNotifications = this.filter { !it.read }
        return Pair(newNotifications, priorNotifications)
    }
}

internal sealed class NotificationBoxIntent : MviIntent

internal data class NotificationBoxState(
    val newNotifications: List<Notification>,
    val priorNotifications: List<Notification>,
) : MviState {
    companion object {
        fun initial() = NotificationBoxState(
            newNotifications = emptyList(),
            priorNotifications = emptyList(),
        )
    }
}

internal sealed class NotificationBoxSideEffect : MviSideEffect {
    object FetchingNotificationsFailed : NotificationBoxSideEffect()
}
