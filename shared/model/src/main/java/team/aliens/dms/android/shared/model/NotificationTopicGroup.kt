package team.aliens.dms.android.shared.model

enum class NotificationTopicGroup {
    NOTICE, STUDY_ROOM, STUDY_ROOM_APPLY,
    ;

    data class Status(
        val topicGroup: NotificationTopicGroup,
        val groupTitle: String,
        val topicSubscriptions: List<TopicSubscription>,
    ) {
        data class TopicSubscription(
            val topic: team.aliens.dms.android.data.notification.model.NotificationTopic,
            val title: String,
            val description: String,
            val subscribed: Boolean,
        )
    }
}
