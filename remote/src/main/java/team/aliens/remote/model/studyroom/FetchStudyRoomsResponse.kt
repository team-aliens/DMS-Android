package team.aliens.remote.model.studyroom

import com.google.gson.annotations.SerializedName
import java.util.*

data class FetchStudyRoomsResponse(
    @SerializedName("study_rooms") val studyRooms: List<StudyRoom>,
) {
    data class StudyRoom(
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
