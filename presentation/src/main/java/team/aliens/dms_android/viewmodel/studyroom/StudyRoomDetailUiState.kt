package team.aliens.dms_android.viewmodel.studyroom

import java.util.*
import team.aliens.dms_android._base.BaseUiState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity

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
                color = "#FFFFFF",
                id = "",
                name = ""
            )
        )
    ),
    var seatBoolean: Boolean = false,
    var studyRoomDetails: StudyRoomDetailEntity = StudyRoomDetailEntity(
        floor = 0,
        name = "",
        startTime = "",
        endTime = "",
        totalAvailableSeat = 0,
        inUseHeadCount = 0,
        availableSex = "",
        availableGrade = "",
        eastDescription = "",
        westDescription = "",
        southDescription = "",
        northDescription = "",
        totalWidthSize = 2,
        totalHeightSize = 2,
        studyRoomSex = "",
        seats = listOf(
            StudyRoomDetailEntity.Seat(
                id = "",
                widthLocation = 1,
                heightLocation = 1,
                number = 0,
                type = StudyRoomDetailEntity.Type(
                    id = "",
                    name = "",
                    color = "#FFFFFF",
                ),
                status = "",
                isMine = false,
                student = StudyRoomDetailEntity.Student(
                    id = "",
                    name = "",
                ),
            ),
        ),
    ),
) : BaseUiState
