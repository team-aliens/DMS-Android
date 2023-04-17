package team.aliens.dms_android.viewmodel.studyroom

import team.aliens.dms_android._base.BaseUiState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.domain._model._common.Sex
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import java.util.UUID

data class StudyRoomDetailUiState(
    var studyRoomId: String = "",
    var timeSlot: UUID? = null,
    var currentSeat: MutableEventFlow<String> = MutableEventFlow(),
    var startAt: String = "",
    var endAt: String = "",
    var errorMessage: MutableEventFlow<String> = MutableEventFlow(),
    var seatType: SeatTypeEntity = SeatTypeEntity(
        types = listOf(
            SeatTypeEntity.Type(
                color = "#FFFFFF", id = "", name = ""
            )
        )
    ),
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
) : BaseUiState
