package team.aliens.dms.android.domain.model.notification

/**
 * An input used when subscribing notification topic
 * @property topic the topic of notification
 */
data class SubscribeNotificationTopicInput(
    val deviceToken: String,
    val topic: NotificationTopic,
)