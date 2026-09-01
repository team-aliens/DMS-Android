package team.aliens.dms.android.data.notification.model

import team.aliens.dms.android.data.point.model.PointType
import java.time.LocalDateTime
import team.aliens.dms.android.network.notification.model.FetchNotificationsResponse
import team.aliens.dms.android.shared.date.toLocalDateTime
import java.util.UUID

data class Notification(
    val id: UUID,
    val topic: NotificationTopic,
    val pointDetailTopic: PointType,
    val linkId: UUID,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)

fun FetchNotificationsResponse.toModel(): List<Notification> =
    this.notifications.mapNotNull(FetchNotificationsResponse.NotificationResponse::toModel)

private fun FetchNotificationsResponse.NotificationResponse.toModel(): Notification? {
    val topic = NotificationTopic.entries.find { it.name == this.topic } ?: return null
    val pointDetailTopic = PointType.entries.find { it.name == (this.pointDetailTopic ?: "ALL") } ?: return null

    return Notification(
        id = this.id,
        topic = topic,
        pointDetailTopic = pointDetailTopic,
        linkId = this.linkId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt.toLocalDateTime(),
        isRead = this.isRead,
    )
}
