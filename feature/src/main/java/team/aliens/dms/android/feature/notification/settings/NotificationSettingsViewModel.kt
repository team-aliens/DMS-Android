package team.aliens.dms.android.feature.notification.settings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
internal class NotificationSettingsViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
) : BaseMviViewModel<NotificationSettingsUiState, NotificationSettingsIntent, NotificationSettingsSideEffect>(
    initialState = NotificationSettingsUiState.initial()
) {

    override fun processIntent(intent: NotificationSettingsIntent) {
        when (intent) {
            is NotificationSettingsIntent.UpdateNotificationTopic -> this.updateNotificationTopic(
                isSubscribed = intent.isSubscribed,
                topic = intent.topic,
            )
        }
    }

    init {
        fetchNotificationsStatus()
    }

    private fun fetchNotificationsStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val deviceToken = notificationRepository.getDeviceToken()
            runCatching {
                notificationRepository.fetchNotificationStatus(deviceToken)
            }.onSuccess {
                reduce(newState = stateFlow.value.copy(status = it))
            }.onFailure {
                postSideEffect(NotificationSettingsSideEffect.CurrentNotificationsStatusNotFound)
            }
        }
    }

    private fun updateNotificationTopic(
        isSubscribed: Boolean,
        topic: NotificationTopic,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val deviceToken = notificationRepository.getDeviceToken()

            if (isSubscribed) {
                subscribeNotificationTopic(
                    deviceToken = deviceToken,
                    topic = topic,
                )
            } else {
                unsubscribeNotificationTopic(
                    deviceToken = deviceToken,
                    topic = topic,
                )
            }
        }
    }

    private fun subscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                notificationRepository.subscribeNotificationTopic(
                    deviceToken = deviceToken,
                    topic = topic,
                )
            }.onFailure {
                postSideEffect(NotificationSettingsSideEffect.SubscribeNotificationFailure)
            }
        }
    }

    private fun unsubscribeNotificationTopic(
        deviceToken: String,
        topic: NotificationTopic,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                notificationRepository.unsubscribeNotificationTopic(
                    deviceToken = deviceToken,
                    topic = topic,
                )
            }.onFailure {
                postSideEffect(NotificationSettingsSideEffect.UnSubscribeNotificationFailure)
            }
        }
    }
}

internal data class NotificationSettingsUiState(
    val status: List<NotificationTopicGroup.Status>,
) : UiState() {
    companion object {
        fun initial(): NotificationSettingsUiState {
            return NotificationSettingsUiState(
                status = listOf(),
            )
        }
    }
}

internal sealed class NotificationSettingsIntent : Intent() {
    class UpdateNotificationTopic(
        val isSubscribed: Boolean,
        val topic: NotificationTopic,
    ) : NotificationSettingsIntent()
}

internal sealed class NotificationSettingsSideEffect : SideEffect() {
    data object CurrentNotificationsStatusNotFound : NotificationSettingsSideEffect()
    data object SubscribeNotificationFailure : NotificationSettingsSideEffect()
    data object UnSubscribeNotificationFailure : NotificationSettingsSideEffect()
}
