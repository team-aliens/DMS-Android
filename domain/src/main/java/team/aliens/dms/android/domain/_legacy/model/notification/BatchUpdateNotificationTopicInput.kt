package team.aliens.dms.android.domain._legacy.model.notification

data class BatchUpdateNotificationTopicInput(
    val topics: List<NotificationTopicInformation>,
) {
    data class NotificationTopicInformation(
        val topic: NotificationTopic,
        val subscribed: Boolean,
    )
}
