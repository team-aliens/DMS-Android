package team.aliens.dms.android.network.notification.model

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.notification.NotificationTopic
import team.aliens.dms.android.domain.model.notification.NotificationTopicGroup

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
