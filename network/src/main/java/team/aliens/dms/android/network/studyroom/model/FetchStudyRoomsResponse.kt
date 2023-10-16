package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchStudyRoomsResponse(
    @SerializedName("study_rooms") val studyRoomResponses: List<StudyRoomResponse>,
) {
    data class StudyRoomResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("floor") val floor: Int,
        @SerializedName("name") val name: String,
        @SerializedName("available_grade") val availableGrade: Int,
        @SerializedName("available_sex") val availableSex: String,
        @SerializedName("in_use_headcount") val inUseHeadcount: Int,
        @SerializedName("total_available_seat") val totalAvailableSeat: Int,
        @SerializedName("is_mine") val isMine: Boolean,
    )
}
