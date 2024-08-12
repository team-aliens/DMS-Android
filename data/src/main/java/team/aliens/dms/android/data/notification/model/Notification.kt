package team.aliens.dms.android.data.notification.model

import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.shared.date.toLocalDateTime
import java.util.UUID

data class Notification(
    val id: UUID,
    val topic: NotificationTopic,
    val linkId: UUID,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)

fun FetchNotificationsResponse.toModel(): List<Notification> =
    this.notifications.map(FetchNotificationsResponse.NotificationResponse::toModel)

private fun FetchNotificationsResponse.NotificationResponse.toModel(): Notification = Notification(
    id = this.id,
    topic = NotificationTopic.valueOf(this.topic),
    linkId = this.linkId,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt.toLocalDateTime(),
    isRead = this.isRead,
)
