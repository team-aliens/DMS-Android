package team.aliens.dms.android.feature.setting.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.device.datastore.DeviceDataStoreDataSource
import team.aliens.dms.android.core.theme.ThemeMode
import team.aliens.dms.android.core.theme.datastore.ThemeDataStoreDataSource
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.data.notification.repository.NotificationRepository
import team.aliens.dms.android.data.student.repository.StudentRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val notificationRepository: NotificationRepository,
    val authRepository: AuthRepository,
    val deviceDataStoreDataSource: DeviceDataStoreDataSource,
    val studentRepository: StudentRepository,
    val themeDataStoreDataSource: ThemeDataStoreDataSource,
): BaseStateViewModel<SettingState, SettingSideEffect>(SettingState()) {

    init {
        fetchDeviceToken()
        observeThemeMode()
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

    private fun observeThemeMode() {
        viewModelScope.launch {
            themeDataStoreDataSource.getThemeModeFlow().collect { mode ->
                setState { it.copy(themeMode = mode) }
            }
        }
    }

    internal fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch(Dispatchers.IO) {
            themeDataStoreDataSource.setThemeMode(mode)
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
        val subscriptions = uiState.value.notificationTopicStatus.flatMap { status ->
            status.topicSubscriptions.map { subscription ->
                NotificationTopic.Subscription(
                    topic = subscription.topic,
                    subscribe = !isOnNotification,
                )
            }
        }

        if (subscriptions.isEmpty()) {
            sendEffect(SettingSideEffect.CannotUpdateNotificationStatus)
            return
        }

        viewModelScope.launch {
            notificationRepository.batchUpdateNotificationTopic(subscriptions)
                .onSuccess {
                    setState { settingState ->
                        settingState.copy(
                            isOnNotification = !isOnNotification,
                            notificationTopicStatus = settingState.notificationTopicStatus.map { status ->
                                status.copy(
                                    topicSubscriptions = status.topicSubscriptions.map { subscription ->
                                        subscription.copy(subscribed = !isOnNotification)
                                    },
                                )
                            },
                        )
                    }
                }
                .onFailure {
                    sendEffect(SettingSideEffect.CannotUpdateNotificationStatus)
                }
        }
    }
}

data class SettingState(
    val deviceToken: String? = null,
    val isOnNotification: Boolean = true,
    val notificationTopicStatus: List<NotificationTopicGroup.Status> = emptyList(),
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
)

sealed class SettingSideEffect {
    object CannotFetchNotificationStatus : SettingSideEffect()
    object CannotUpdateNotificationStatus : SettingSideEffect()
    object SignOutSuccess : SettingSideEffect()
    object WithdrawSuccess : SettingSideEffect()
    object WithdrawFailed : SettingSideEffect()
}
