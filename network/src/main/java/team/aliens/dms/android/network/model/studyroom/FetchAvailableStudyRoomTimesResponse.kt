package team.aliens.dms.android.network.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import java.util.*

data class FetchAvailableStudyRoomTimesResponse(
    @SerializedName("time_slots") val timeSlots: List<TimeSlotResponse>,
) {
    data class TimeSlotResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("start_time") val startTime: String,
        @SerializedName("end_time") val endTime: String,
    )
}

internal fun FetchAvailableStudyRoomTimesResponse.toDomain(): FetchAvailableStudyRoomTimesOutput {
    return FetchAvailableStudyRoomTimesOutput(
        timeSlots = this.timeSlots.toDomain(),
    )
}

internal fun FetchAvailableStudyRoomTimesResponse.TimeSlotResponse.toDomain(): FetchAvailableStudyRoomTimesOutput.TimeSlotInformation {
    return FetchAvailableStudyRoomTimesOutput.TimeSlotInformation(
        id = this.id,
        startTime = this.startTime,
        endTime = this.endTime,
    )
}

internal fun List<FetchAvailableStudyRoomTimesResponse.TimeSlotResponse>.toDomain(): List<FetchAvailableStudyRoomTimesOutput.TimeSlotInformation> {
    return this.map(FetchAvailableStudyRoomTimesResponse.TimeSlotResponse::toDomain)
}
