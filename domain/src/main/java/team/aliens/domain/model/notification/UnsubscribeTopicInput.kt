package team.aliens.domain.model.notification

import team.aliens.domain.model._common.NotificationTopic

/**
 * An input used when unsubscribing notification topic
 * @property topic the topic of notification
 */
data class UnsubscribeTopicInput(
    val topic: NotificationTopic,
)
