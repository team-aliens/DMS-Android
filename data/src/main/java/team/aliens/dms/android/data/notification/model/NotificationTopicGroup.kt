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
    this.map(FetchNotificationTopicStatusResponse.TopicGroupResponse::toModel)

private fun FetchNotificationTopicStatusResponse.TopicGroupResponse.toModel(): NotificationTopicGroup.Status =
    NotificationTopicGroup.Status(
        topicGroup = NotificationTopicGroup.valueOf(this.topicGroup),
        groupName = this.groupName,
        topicSubscriptions = this.topicSubscriptions.toModel(),
    )

private fun List<FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse>.toModel(): List<NotificationTopicGroup.Status.TopicSubscription> =
    this.map(FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse::toModel)

private fun FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse.toModel(): NotificationTopicGroup.Status.TopicSubscription =
    NotificationTopicGroup.Status.TopicSubscription(
        topic = NotificationTopic.valueOf(this.topic),
        subscribed = this.subscribed,
    )
