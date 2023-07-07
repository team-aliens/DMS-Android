package team.aliens.domain.model.notification

import java.util.UUID
import team.aliens.domain.model._common.NotificationTopic

data class FetchReceivedNotificationsOutput(
    val notifications: List<NotificationInformation>,
) {
    data class NotificationInformation(
        val id: UUID,
        val topic: NotificationTopic,
        val linkId: UUID,
        val title: String,
        val content: String,
        val createdAt: String,
    )
}

fun FetchReceivedNotificationsOutput.NotificationInformation.toModel(): Notification {
    return Notification(
        id = this.id,
        topic = this.topic,
        linkId = this.linkId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}

fun List<FetchReceivedNotificationsOutput.NotificationInformation>.toModel(): List<Notification> {
    return this.map(FetchReceivedNotificationsOutput.NotificationInformation::toModel)
}

fun FetchReceivedNotificationsOutput.toModel(): List<Notification> {
    return this.notifications.toModel()
}
