package team.aliens.remote.model.school

import com.google.gson.annotations.SerializedName

data class FetchAvailableFeaturesResponse(
    @SerializedName("meal_service") val mealService: Boolean,
    @SerializedName("notice_service") val noticeService: Boolean,
    @SerializedName("point_service") val pointService: Boolean,
    @SerializedName("study_room_service") val studyRoomService: Boolean,
    @SerializedName("remain_service") val remainService: Boolean,
)
