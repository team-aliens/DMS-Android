package team.aliens.network.model._common

import com.google.gson.annotations.SerializedName

data class FeaturesResponse(
    @SerializedName("meal_service") val mealService: Boolean,
    @SerializedName("notice_service") val noticeService: Boolean,
    @SerializedName("point_service") val pointService: Boolean,
    @SerializedName("study_room_service") val studyRoomService: Boolean,
    @SerializedName("remain_service") val remainsService: Boolean,
)
