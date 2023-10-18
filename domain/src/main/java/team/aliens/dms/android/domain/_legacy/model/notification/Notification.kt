package team.aliens.dms.android.domain._legacy.model.notification

import java.util.UUID

data class Notification(
    val id: UUID,
    val topic: NotificationTopic,
    val linkId: UUID,
    val title: String,
    val content: String,
    val createdAt: String,
    val read: Boolean,
)

enum class NotificationTopic {
    NOTICE, ;
}

enum class NotificationTopicGroup {
    NOTICE, STUDY_ROOM, ;
}
