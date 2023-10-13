package team.aliens.dms.android.network.datasource

import team.aliens.dms_android.data.datasource.remote.RemoteNotificationDataSource
import team.aliens.dms.android.network.apiservice.NotificationApiService
import team.aliens.dms.android.network.model.notification.RegisterDeviceNotificationTokenRequest
import team.aliens.dms.android.network.model.notification.toData
import team.aliens.dms.android.network.model.notification.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.domain.model.notification.BatchUpdateNotificationTopicInput
import team.aliens.dms.android.domain.model.notification.CancelDeviceTokenRegistrationInput
import team.aliens.dms.android.domain.model.notification.FetchNotificationTopicStatusInput
import team.aliens.dms.android.domain.model.notification.FetchNotificationTopicStatusOutput
import team.aliens.dms.android.domain.model.notification.FetchNotificationsOutput
import team.aliens.dms.android.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.dms.android.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.dms.android.domain.model.notification.UnsubscribeNotificationTopicInput
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
        return sendHttpRequest { notificationApiService.fetchNotifications() }.toDomain()
    }
}
