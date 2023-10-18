package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName

data class FetchNotificationTopicStatusResponse(
    @SerializedName("topic_groups") val topicGroups: List<TopicGroupResponse>,
) {
    data class TopicGroupResponse(
        @SerializedName("topic_group") val topicGroup: NotificationTopicGroup,
        @SerializedName("group_title") val groupTitle: String,
        @SerializedName("topic_subscriptions") val topicSubscriptions: List<TopicSubscriptionResponse>,
    ) {
        data class TopicSubscriptionResponse(
            @SerializedName("topic") val topic: String,
            @SerializedName("title") val title: String,
            @SerializedName("description") val description: String,
            @SerializedName("is_subscribed") val subscribed: Boolean,
        )
    }
}
