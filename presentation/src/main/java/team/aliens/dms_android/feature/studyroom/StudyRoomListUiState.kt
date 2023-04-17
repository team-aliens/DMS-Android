package team.aliens.dms_android.feature.studyroom

import team.aliens.dms_android._base.BaseUiState
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import java.util.UUID

data class StudyRoomListUiState(
    var startAt: String = "",
    var endAt: String = "",
    var studyRooms: List<StudyRoomInformation> = emptyList(),
    var studyRoomAvailableTime: List<FetchAvailableStudyRoomTimesOutput.TimeSlotInformation> = emptyList(),
    var timeSlot: UUID? = null,
    var hasApplyTime: Boolean = false,
) : BaseUiState

data class StudyRoomDetailsUiState(
    var studyRoomDetailsEntity: StudyRoomDetailEntity? = null,
)

data class StudyRoomInformation(
    val roomId: String,
    val position: String,
    val title: String,
    val currentNumber: Int,
    val isMine: Boolean,
    val maxNumber: Int,
    val condition: String,
)

internal fun StudyRoomListEntity.StudyRoom?.toInformation(): StudyRoomInformation {
    requireNotNull(this)

    return StudyRoomInformation(
        roomId = id,
        position = "${floor}층",
        title = name,
        currentNumber = inUseHeadcount,
        isMine = isMine,
        maxNumber = totalAvailableSeat,
        condition = "${availableGrade}학년 $studyRoomSex",
    )
}

internal fun List<StudyRoomListEntity.StudyRoom?>.toInformation(): List<StudyRoomInformation> {
    return if (this.isNotEmpty()) {
        this.map { it.toInformation() }
    } else {
        return emptyList()
    }
}
