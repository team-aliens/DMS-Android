package team.aliens.dms_android.feature.main.studyroom

import team.aliens.dms_android.base.UiState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.domain.model._common.Sex
import team.aliens.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.domain.model.studyroom.FetchStudyRoomDetailsOutput
import java.util.UUID

data class StudyRoomDetailUiState(
    var studyRoomId: String = "",
    var timeSlot: UUID? = null,
    var currentSeat: MutableEventFlow<String> = MutableEventFlow(),
    var startAt: String = "",
    var endAt: String = "",
    var errorMessage: MutableEventFlow<String> = MutableEventFlow(),
    var seatType: FetchSeatTypesOutput = FetchSeatTypesOutput(listOf()),
    var seatBoolean: Boolean = false,
    var studyRoomDetails: FetchStudyRoomDetailsOutput = FetchStudyRoomDetailsOutput(
        floor = 0,
        name = "",
        startTime = "",
        endTime = "",
        totalAvailableSeat = 0,
        inUseHeadcount = 0,
        availableSex = Sex.ALL,
        availableGrade = 0,
        eastDescription = "",
        westDescription = "",
        southDescription = "",
        northDescription = "",
        totalWidthSize = 0,
        totalHeightSize = 0,
        seats = listOf(),
    ),
) : UiState