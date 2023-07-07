package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.domain.model.notification.BatchUpdateNotificationTopicInput
import team.aliens.domain.model.notification.CancelDeviceTokenRegistrationInput
import team.aliens.domain.model.notification.FetchNotificationTopicStatusInput
import team.aliens.domain.model.notification.FetchNotificationTopicStatusOutput
import team.aliens.domain.model.notification.FetchNotificationsOutput
import team.aliens.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.domain.model.notification.UnsubscribeNotificationTopicInput
import team.aliens.remote.apiservice.NotificationApiService
import team.aliens.remote.model.notification.RegisterDeviceNotificationTokenRequest
import team.aliens.remote.model.notification.toData
import team.aliens.remote.model.notification.toDomain
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteNotificationDataSourceImpl @Inject constructor(
    private val notificationApiService: NotificationApiService,
) : RemoteNotificationDataSource {
    override suspend fun registerDeviceNotificationToken(
        input: RegisterDeviceNotificationTokenInput,
    ) {
        sendHttpRequest {
            notificationApiService.registerDeviceNotificationToken(
                request = RegisterDeviceNotificationTokenRequest(
                    deviceToken = input.deviceToken,
                    deviceId = input.deviceId,
                    operatingSystem = input.operatingSystem,
                ),
            )
        }
    }

    override suspend fun cancelDeviceTokenRegistration(
        input: CancelDeviceTokenRegistrationInput,
    ) {
        sendHttpRequest {
            notificationApiService.cancelDeviceTokenRegistration(
                request = input.toData(),
            )
        }
    }

    override suspend fun subscribeNotificationTopic(
        input: SubscribeNotificationTopicInput,
    ) {
        sendHttpRequest {
            notificationApiService.subscribeNotificationTopic(
                request = input.toData(),
            )
        }
    }

    override suspend fun unsubscribeNotificationTopic(
        input: UnsubscribeNotificationTopicInput,
    ) {
        sendHttpRequest {
            notificationApiService.unsubscribeNotificationTopic(
                request = input.toData(),
            )
        }
    }

    override suspend fun batchUpdateNotificationTopic(
        input: BatchUpdateNotificationTopicInput,
    ) {
        sendHttpRequest {
            notificationApiService.batchUpdateNotificationTopic(
                request = input.toData(),
            )
        }
    }

    override suspend fun fetchNotificationTopicStatus(
        input: FetchNotificationTopicStatusInput,
    ): FetchNotificationTopicStatusOutput {
        return sendHttpRequest {
            notificationApiService.fetchNotificationTopicStatus(
                request = input.toData(),
            )
        }.toDomain()
    }

    override suspend fun fetchNotifications(): FetchNotificationsOutput {
        return sendHttpRequest {
            notificationApiService.fetchNotifications()
        }.toDomain()
    }
}
