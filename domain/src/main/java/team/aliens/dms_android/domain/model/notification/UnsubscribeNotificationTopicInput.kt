package team.aliens.dms_android.domain.model.notification

/**
 * An input used when unsubscribing notification topic
 * @property topic the topic of notification
 */
data class UnsubscribeNotificationTopicInput(
    val deviceToken: String,
    val topic: NotificationTopic,
)
