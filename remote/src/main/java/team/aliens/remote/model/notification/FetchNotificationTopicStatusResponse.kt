package team.aliens.remote.model.notification

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notification.FetchNotificationTopicStatusOutput
import team.aliens.domain.model.notification.NotificationTopic
import team.aliens.domain.model.notification.NotificationTopicGroup

data class FetchNotificationTopicStatusResponse(
    @SerializedName("topic_groups") val topicGroups: List<TopicGroupResponse>,
) {
    data class TopicGroupResponse(
        @SerializedName("topic_group") val topicGroup: NotificationTopicGroup,
        @SerializedName("group_title") val groupTitle: String,
        @SerializedName("topic_subscriptions") val topicSubscriptions: List<TopicSubscriptionResponse>,
    ) {
        data class TopicSubscriptionResponse(
            @SerializedName("topic") val topic: NotificationTopic,
            @SerializedName("title") val title: String,
            @SerializedName("description") val description: String,
            @SerializedName("is_subscribed") val subscribed: Boolean,
        )
    }
}

fun FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse.toDomain(): FetchNotificationTopicStatusOutput.TopicGroupInformation.TopicSubscriptionInformation {
    return FetchNotificationTopicStatusOutput.TopicGroupInformation.TopicSubscriptionInformation(
        topic = topic,
        title = title,
        description = description,
        subscribed = subscribed,
    )
}

fun List<FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse>.toDomain(): List<FetchNotificationTopicStatusOutput.TopicGroupInformation.TopicSubscriptionInformation> {
    return this.map(FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse::toDomain)
}

fun FetchNotificationTopicStatusResponse.TopicGroupResponse.toDomain(): FetchNotificationTopicStatusOutput.TopicGroupInformation {
    return FetchNotificationTopicStatusOutput.TopicGroupInformation(
        topicGroup = topicGroup,
        groupTitle = groupTitle,
        topicSubscriptions = topicSubscriptions.toDomain(),
    )
}

fun List<FetchNotificationTopicStatusResponse.TopicGroupResponse>.toDomain(): List<FetchNotificationTopicStatusOutput.TopicGroupInformation> {
    return this.map(FetchNotificationTopicStatusResponse.TopicGroupResponse::toDomain)
}

fun FetchNotificationTopicStatusResponse.toDomain(): FetchNotificationTopicStatusOutput {
    return FetchNotificationTopicStatusOutput(
        topicGroups = this.topicGroups.toDomain(),
    )
}
