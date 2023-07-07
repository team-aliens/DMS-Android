package team.aliens.domain.model.notification

import java.util.UUID
import team.aliens.domain.model._common.NotificationTopic

data class Notification(
    val id: UUID,
    val topic: NotificationTopic,
    val linkId: UUID,
    val title: String,
    val content: String,
    val createdAt: String,
)
