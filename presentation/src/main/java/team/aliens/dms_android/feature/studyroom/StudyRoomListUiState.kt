package team.aliens.dms_android.feature.studyroom

import team.aliens.dms_android._base.UiState
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import java.util.UUID

data class StudyRoomListUiState(
    var startAt: String = "",
    var endAt: String = "",
    var studyRooms: List<StudyRoomInformation> = emptyList(),
    var studyRoomAvailableTime: List<FetchAvailableStudyRoomTimesOutput.TimeSlotInformation> = emptyList(),
    var timeSlot: UUID? = null,
    var hasApplyTime: Boolean = false,
) : UiState

data class StudyRoomDetailsUiState(
    var studyRoomDetailsEntity: StudyRoomDetailEntity? = null,
)

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
