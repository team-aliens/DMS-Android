package team.aliens.dms.android.data.notification.model

enum class NotificationTopicGroup {
    NOTICE, STUDY_ROOM, STUDY_ROOM_APPLY,
    ;

    data class Status(
        val topicGroup: NotificationTopicGroup,
        val groupTitle: String,
        val topicSubscriptions: List<TopicSubscription>,
    ) {
        data class TopicSubscription(
            val topic: NotificationTopic,
            val title: String,
            val description: String,
            val subscribed: Boolean,
        )
    }
}
