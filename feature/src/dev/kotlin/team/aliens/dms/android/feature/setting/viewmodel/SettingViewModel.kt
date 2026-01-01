package team.aliens.dms.android.feature.setting.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSource
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.data.notification.model.toModel
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val notificationRepository: NotificationRepository,
    val deviceDataStoreDataSource: DeviceDataStoreDataSource,
): BaseStateViewModel<SettingState, SettingSideEffect>(SettingState()) {

    init {
        fetchDeviceToken()
        fetchNotificationStatus()
    }

    private fun fetchDeviceToken() {
        viewModelScope.launch {
            runCatching {
                deviceDataStoreDataSource.loadDeviceToken()
            }.onSuccess { deviceToken ->
                setState { settingState ->
                    settingState.copy(
                        deviceToken = deviceToken,
                    )
                }
            }

        }
    }

    private fun fetchNotificationStatus() {
        viewModelScope.launch {
            notificationRepository.fetchNotificationStatus(uiState.value.deviceToken.toString()).onSuccess { statuses ->
                val isOnNotification = statuses.any { status ->
                    status.topicSubscriptions.any { subscription ->
                        subscription.subscribed
                    }
                }
                setState { settingState -> settingState.copy(notificationTopicStatus = statuses, isOnNotification = isOnNotification) }
            }.onFailure {
                setState { settingState -> settingState.copy(isOnNotification = false) }
                sendEffect(SettingSideEffect.CannotFetchNotificationStatus)
            }
        }
    }

    internal fun updateNotificationStatus(isOnNotification: Boolean) {
        setState { settingState ->
            settingState.copy(isOnNotification = !isOnNotification)
        }
        if (isOnNotification) /* 구독 취소 */ setNotificationStatus() else return // TODO 구독 업데이트 (false -> true)
    }

    private fun setNotificationStatus() {
        Log.d("TEST", "실행됨 : ${uiState.value.notificationTopicStatus}")
    }
}

data class SettingState(
    val deviceToken: String? = null,
    val isOnNotification: Boolean = false,
    val notificationTopicStatus: List<NotificationTopicGroup.Status> = emptyList()
)

sealed class SettingSideEffect {
    object CannotFetchNotificationStatus : SettingSideEffect()
}
