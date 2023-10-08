package team.aliens.dms_android.feature._feature.main.studyroom

import java.util.UUID
import team.aliens.dms_android.feature.base.UiState
import team.aliens.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain.model.studyroom.FetchStudyRoomsOutput

data class StudyRoomListUiState(
    var startAt: String = "",
    var endAt: String = "",
    var studyRooms: List<StudyRoomInformation> = emptyList(),
    var studyRoomAvailableTime: List<FetchAvailableStudyRoomTimesOutput.TimeSlotInformation> = emptyList(),
    var timeSlot: UUID? = null,
    var hasApplyTime: Boolean = false,
) : UiState

data class StudyRoomInformation(
    val roomId: UUID,
    val position: String,
    val title: String,
    val currentNumber: Int,
    val isMine: Boolean,
    val maxNumber: Int,
    val condition: String,
)

internal fun FetchStudyRoomsOutput.StudyRoomInformation?.toInformation(): StudyRoomInformation {
    requireNotNull(this)

    val grade = if (availableGrade == 0) "모든" else availableGrade

    return StudyRoomInformation(
        roomId = id,
        position = "${floor}층",
        title = name,
        currentNumber = inUseHeadcount,
        isMine = isMine,
        maxNumber = totalAvailableSeat,
        condition = "${grade}학년 ${availableSex.koreanValue}",
    )
}

internal fun List<FetchStudyRoomsOutput.StudyRoomInformation?>.toInformation(): List<StudyRoomInformation> {
    return if (this.isNotEmpty()) {
        this.map { it.toInformation() }
    } else {
        return emptyList()
    }
}
