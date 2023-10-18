package team.aliens.dms.android.shared.model

enum class NotificationTopic {
    NOTICE, STUDY_ROOM,
    ;

    data class Subscription(
        val topic: NotificationTopic,
        val subscribe: Boolean,
    )
}
