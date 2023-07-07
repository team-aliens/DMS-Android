package team.aliens.domain.model.notification

import team.aliens.domain.model.notification.NotificationTopic

/**
 * An input used when subscribing notification topic
 * @property topic the topic of notification
 */
data class SubscribeNotificationTopicInput(
    val topic: NotificationTopic,
)
