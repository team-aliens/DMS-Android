package team.aliens.dms.android.domain._legacy.model.notification

data class FetchNotificationTopicStatusOutput(
    val topicGroups: List<TopicGroupInformation>,
) {
    data class TopicGroupInformation(
        val topicGroup: NotificationTopicGroup,
        val groupTitle: String,
        val topicSubscriptions: List<TopicSubscriptionInformation>,
    ) {
        data class TopicSubscriptionInformation(
            val topic: NotificationTopic,
            val title: String,
            val description: String,
            val subscribed: Boolean,
        )
    }
}
