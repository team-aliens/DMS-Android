package team.aliens.dms.android.network._legacy.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model._common.Sex
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsOutput.SeatInformation.SeatStatus
import java.util.*

data class FetchStudyRoomDetailsResponse(
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
        @SerializedName("is_mine") val isMine: Boolean?,
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

internal fun FetchStudyRoomDetailsResponse.toDomain(): FetchStudyRoomDetailsOutput {
    return FetchStudyRoomDetailsOutput(
        floor = this.floor,
        name = this.name,
        startTime = this.startTime,
        endTime = this.endTime,
        inUseHeadcount = this.inUseHeadcount,
        totalAvailableSeat = this.totalAvailableSeat,
        availableSex = Sex.valueOf(this.availableSex),
        availableGrade = this.availableGrade,
        eastDescription = this.eastDescription,
        westDescription = this.westDescription,
        southDescription = this.southDescription,
        northDescription = this.northDescription,
        totalWidthSize = this.totalWidthSize,
        totalHeightSize = this.totalHeightSize,
        seats = this.seats.toDomain(),
    )
}

internal fun FetchStudyRoomDetailsResponse.SeatResponse.toDomain(): FetchStudyRoomDetailsOutput.SeatInformation {
    return FetchStudyRoomDetailsOutput.SeatInformation(
        id = this.id,
        row = this.row,
        column = this.column,
        number = this.number,
        type = this.type.toDomain(),
        status = SeatStatus.valueOf(this.status),
        isMine = this.isMine,
        student = this.student.toDomain(),
    )
}

internal fun FetchStudyRoomDetailsResponse.SeatResponse.SeatTypeResponse?.toDomain(): FetchStudyRoomDetailsOutput.SeatInformation.SeatType? {
    return if (this != null) {
        FetchStudyRoomDetailsOutput.SeatInformation.SeatType(
            id = this.id,
            name = this.name,
            color = this.color,
        )
    } else null
}

internal fun FetchStudyRoomDetailsResponse.SeatResponse.StudentResponse?.toDomain(): FetchStudyRoomDetailsOutput.SeatInformation.StudentInformation? {
    return if (this != null) {
        FetchStudyRoomDetailsOutput.SeatInformation.StudentInformation(
            id = this.id,
            name = this.name,
        )
    } else null
}

internal fun List<FetchStudyRoomDetailsResponse.SeatResponse>.toDomain(): List<FetchStudyRoomDetailsOutput.SeatInformation> {
    return this.map(FetchStudyRoomDetailsResponse.SeatResponse::toDomain)
}
