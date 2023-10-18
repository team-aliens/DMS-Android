package team.aliens.dms.android.data.notification.model

import team.aliens.dms.android.shared.model.NotificationTopic
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