package team.aliens.dms_android.feature._feature.studyroom

import java.util.UUID
import team.aliens.dms_android.feature.base.UiState
import team.aliens.dms_android.feature.util.MutableEventFlow
import team.aliens.dms_android.domain.model._common.Sex
import team.aliens.dms_android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomDetailsOutput

data class StudyRoomDetailUiState(
    var studyRoomId: UUID? = null,
    var timeSlot: UUID? = null,
    var currentSeat: MutableEventFlow<UUID> = MutableEventFlow(),
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
