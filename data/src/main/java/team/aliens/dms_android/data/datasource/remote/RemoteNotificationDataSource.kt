package team.aliens.dms_android.data.datasource.remote

import team.aliens.dms_android.domain.model.notification.BatchUpdateNotificationTopicInput
import team.aliens.dms_android.domain.model.notification.CancelDeviceTokenRegistrationInput
import team.aliens.dms_android.domain.model.notification.FetchNotificationTopicStatusInput
import team.aliens.dms_android.domain.model.notification.FetchNotificationTopicStatusOutput
import team.aliens.dms_android.domain.model.notification.FetchNotificationsOutput
import team.aliens.dms_android.domain.model.notification.RegisterDeviceNotificationTokenInput
import team.aliens.dms_android.domain.model.notification.SubscribeNotificationTopicInput
import team.aliens.dms_android.domain.model.notification.UnsubscribeNotificationTopicInput

interface RemoteNotificationDataSource {
    suspend fun registerDeviceNotificationToken(
        input: RegisterDeviceNotificationTokenInput,
    )

    suspend fun cancelDeviceTokenRegistration(
        input: CancelDeviceTokenRegistrationInput,
    )

    suspend fun subscribeNotificationTopic(
        input: SubscribeNotificationTopicInput,
    )

    suspend fun unsubscribeNotificationTopic(
        input: UnsubscribeNotificationTopicInput,
    )

    suspend fun batchUpdateNotificationTopic(
        input: BatchUpdateNotificationTopicInput,
    )

    suspend fun fetchNotificationTopicStatus(
        input: FetchNotificationTopicStatusInput,
    ): FetchNotificationTopicStatusOutput

    suspend fun fetchNotifications(): FetchNotificationsOutput
}
