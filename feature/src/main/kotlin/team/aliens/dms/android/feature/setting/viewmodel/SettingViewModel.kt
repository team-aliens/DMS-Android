package team.aliens.dms.android.feature.setting.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSource
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val notificationRepository: NotificationRepository,
    val authRepository: AuthRepository,
    val deviceDataStoreDataSource: DeviceDataStoreDataSource,
    val studentRepository: StudentRepository,
): BaseStateViewModel<SettingState, SettingSideEffect>(SettingState()) {

    init {
        fetchDeviceToken()
    }

    private fun fetchDeviceToken() {
        viewModelScope.launch {
            deviceDataStoreDataSource.loadDeviceToken().onSuccess { deviceToken ->
                setState { settingState ->
                    settingState.copy(
                        deviceToken = deviceToken,
                    )
                }
                fetchNotificationStatus()
            }
        }
    }

    internal fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signOut().onSuccess {
                sendEffect(SettingSideEffect.SignOutSuccess)
            }
        }
    }

    internal fun withdraw() {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.withdraw()
                .onSuccess { sendEffect(SettingSideEffect.WithdrawSuccess) }
                .onFailure { sendEffect(SettingSideEffect.WithdrawFailed) }
        }
    }

    private fun fetchNotificationStatus() {
        viewModelScope.launch {
            val deviceToken = uiState.value.deviceToken ?: return@launch
            notificationRepository.fetchNotificationStatus(deviceToken).onSuccess { statuses ->
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
        // TODO: implement notification subscription update
    }
}

data class SettingState(
    val deviceToken: String? = null,
    val isOnNotification: Boolean = true,
    val notificationTopicStatus: List<NotificationTopicGroup.Status> = emptyList()
)

sealed class SettingSideEffect {
    object CannotFetchNotificationStatus : SettingSideEffect()
    object SignOutSuccess : SettingSideEffect()
    object WithdrawSuccess : SettingSideEffect()
    object WithdrawFailed : SettingSideEffect()
}
