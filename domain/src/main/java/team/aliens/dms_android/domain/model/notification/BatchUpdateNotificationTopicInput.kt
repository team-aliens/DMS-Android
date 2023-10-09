package team.aliens.dms_android.domain.model.notification

data class BatchUpdateNotificationTopicInput(
    val topics: List<NotificationTopicInformation>,
) {
    data class NotificationTopicInformation(
        val topic: NotificationTopic,
        val subscribed: Boolean,
    )
}
