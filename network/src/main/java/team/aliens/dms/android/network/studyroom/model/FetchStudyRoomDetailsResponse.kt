package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchStudyRoomDetailsResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("floor") val floor: Int,
    @SerializedName("name") val name: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("in_use_headcount") val inUseHeadcount: Int,
    @SerializedName("total_available_seat") val totalAvailableSeat: Int,
    @SerializedName("available_sex") val availableSex: String,
    @SerializedName("available_grade") val availableGrade: Int,
    @SerializedName("east_description") val eastDescription: String,
    @SerializedName("west_description") val westDescription: String,
    @SerializedName("south_description") val southDescription: String,
    @SerializedName("north_description") val northDescription: String,
    @SerializedName("total_width_size") val totalWidthSize: Int,
    @SerializedName("total_height_size") val totalHeightSize: Int,
    @SerializedName("seats") val seats: List<SeatResponse>,
) {
    data class SeatResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("width_location") val row: Int,
        @SerializedName("height_location") val column: Int,
        @SerializedName("number") val number: Int?,
        @SerializedName("type") val type: SeatTypeResponse?,
        @SerializedName("status") val status: String,
        @SerializedName("is_mine") val isMine: Boolean,
        @SerializedName("student") val student: StudentResponse?,
    ) {

        data class SeatTypeResponse(
            @SerializedName("id") val id: UUID,
            @SerializedName("name") val name: String,
            @SerializedName("color") val color: String,
        )

        data class StudentResponse(
            @SerializedName("id") val id: UUID,
            @SerializedName("name") val name: String,
        )
    }
}
