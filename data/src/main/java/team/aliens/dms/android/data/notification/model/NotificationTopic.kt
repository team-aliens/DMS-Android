package team.aliens.dms.android.data.notification.model

enum class NotificationTopic {
    NOTICE, STUDY_ROOM_TIME_SLOT, STUDY_ROOM_APPLY, POINT, OUTING,
    ;

    data class Subscription(
        val topic: NotificationTopic,
        val subscribe: Boolean,
    )
}
