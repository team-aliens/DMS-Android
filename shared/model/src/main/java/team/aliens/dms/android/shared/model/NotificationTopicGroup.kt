package team.aliens.dms.android.shared.model

enum class NotificationTopicGroup {
    NOTICE, STUDY_ROOM, STUDY_ROOM_APPLY,
    ;

    data class Information(
        val topicGroup: NotificationTopicGroup,
        val groupTitle: String,
        val topicSubscriptions: List<Subscription>,
    ) {
        data class Subscription(
            val topic: NotificationTopic,
            val title: String,
            val description: String,
            val subscribed: Boolean,
        )
    }
}
