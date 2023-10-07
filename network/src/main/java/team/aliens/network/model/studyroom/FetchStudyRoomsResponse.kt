package team.aliens.network.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model._common.Sex
import team.aliens.domain.model.studyroom.FetchStudyRoomsOutput
import java.util.*

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

internal fun FetchStudyRoomsResponse.toDomain(): FetchStudyRoomsOutput {
    return FetchStudyRoomsOutput(
        studyRooms = this.studyRoomResponses.toDomain(),
    )
}

internal fun FetchStudyRoomsResponse.StudyRoomResponse.toDomain(): FetchStudyRoomsOutput.StudyRoomInformation {
    return FetchStudyRoomsOutput.StudyRoomInformation(
        id = this.id,
        floor = this.floor,
        name = this.name,
        availableGrade = this.availableGrade,
        availableSex = Sex.valueOf(this.availableSex),
        inUseHeadcount = this.inUseHeadcount,
        totalAvailableSeat = this.totalAvailableSeat,
        isMine = this.isMine,
    )
}

internal fun List<FetchStudyRoomsResponse.StudyRoomResponse>.toDomain(): List<FetchStudyRoomsOutput.StudyRoomInformation> {
    return this.map(FetchStudyRoomsResponse.StudyRoomResponse::toDomain)
}
