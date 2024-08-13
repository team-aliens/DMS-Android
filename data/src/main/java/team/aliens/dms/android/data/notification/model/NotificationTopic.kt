package team.aliens.dms.android.data.notification.model

import team.aliens.dms.android.network.notification.model.BatchUpdateNotificationTopicRequest

enum class NotificationTopic {
    NOTICE, STUDY_ROOM_TIME_SLOT, STUDY_ROOM_APPLY, POINT, OUTING,
    ;

    data class Subscription(
        val topic: NotificationTopic,
        val subscribe: Boolean,
    )
}

fun List<NotificationTopic.Subscription>.toModel(): List<BatchUpdateNotificationTopicRequest.NotificationTopicRequest> =
    this.map(NotificationTopic.Subscription::toModel)

private fun NotificationTopic.Subscription.toModel(): BatchUpdateNotificationTopicRequest.NotificationTopicRequest =
    BatchUpdateNotificationTopicRequest.NotificationTopicRequest(
        topic = this.topic.name,
        subscribed = this.subscribe,
    )
