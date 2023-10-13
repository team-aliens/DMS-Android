package team.aliens.dms.android.network.model.school

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.school.FetchAvailableFeaturesOutput

data class FetchAvailableFeaturesResponse(
    @SerializedName("meal_service") val mealService: Boolean,
    @SerializedName("notice_service") val noticeService: Boolean,
    @SerializedName("point_service") val pointService: Boolean,
    @SerializedName("study_room_service") val studyRoomService: Boolean,
    @SerializedName("remain_service") val remainService: Boolean,
)

internal fun FetchAvailableFeaturesResponse.toDomain(): FetchAvailableFeaturesOutput {
    return FetchAvailableFeaturesOutput(
        mealService = this.mealService,
        noticeService = this.noticeService,
        pointService = this.pointService,
        studyRoomService = this.studyRoomService,
        remainService = this.remainService,
    )
}
