package team.aliens.domain.model.notification

import java.util.UUID

data class FetchNotificationsOutput(
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

fun FetchNotificationsOutput.NotificationInformation.toModel(): Notification {
    return Notification(
        id = this.id,
        topic = this.topic,
        linkId = this.linkId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}

fun List<FetchNotificationsOutput.NotificationInformation>.toModel(): List<Notification> {
    return this.map(FetchNotificationsOutput.NotificationInformation::toModel)
}

fun FetchNotificationsOutput.toModel(): List<Notification> {
    return this.notifications.toModel()
}
