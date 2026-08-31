package team.aliens.dms.android.data.notification.model

import team.aliens.dms.android.network.notification.model.FetchNotificationTopicStatusResponse

enum class NotificationTopicGroup {
    NOTICE, STUDY_ROOM, POINT, OUTING,
    ;

    data class Status(
        val topicGroup: NotificationTopicGroup,
        val groupName: String,
        val topicSubscriptions: List<TopicSubscription>,
    ) {
        data class TopicSubscription(
            val topic: NotificationTopic,
            val subscribed: Boolean,
        )
    }
}

fun FetchNotificationTopicStatusResponse.toModel(): List<NotificationTopicGroup.Status> =
    this.topicGroups.toModel()

@JvmName("ListTopicGroupResponse")
private fun List<FetchNotificationTopicStatusResponse.TopicGroupResponse>.toModel(): List<NotificationTopicGroup.Status> =
    this.mapNotNull(FetchNotificationTopicStatusResponse.TopicGroupResponse::toModel)

private fun FetchNotificationTopicStatusResponse.TopicGroupResponse.toModel(): NotificationTopicGroup.Status? {
    val topicGroup = NotificationTopicGroup.entries.find { it.name == this.topicGroup } ?: return null

    return NotificationTopicGroup.Status(
        topicGroup = topicGroup,
        groupName = this.groupName,
        topicSubscriptions = this.topicSubscriptions.toModel(),
    )
}

private fun List<FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse>.toModel(): List<NotificationTopicGroup.Status.TopicSubscription> =
    this.mapNotNull(FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse::toModel)

private fun FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse.toModel(): NotificationTopicGroup.Status.TopicSubscription? {
    val topic = NotificationTopic.entries.find { it.name == this.topic } ?: return null

    return NotificationTopicGroup.Status.TopicSubscription(
        topic = topic,
        subscribed = this.subscribed,
    )
}
