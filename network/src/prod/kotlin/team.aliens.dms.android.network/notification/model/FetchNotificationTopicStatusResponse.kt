package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName

data class FetchNotificationTopicStatusResponse(
    @SerializedName("topic_groups") val topicGroups: List<TopicGroupResponse>,
) {
    data class TopicGroupResponse(
        @SerializedName("topic_group") val topicGroup: String,
        @SerializedName("group_name") val groupName: String,
        @SerializedName("topic_subscriptions") val topicSubscriptions: List<TopicSubscriptionResponse>,
    ) {
        data class TopicSubscriptionResponse(
            @SerializedName("topic") val topic: String,
            @SerializedName("is_subscribed") val subscribed: Boolean,
        )
    }
}
