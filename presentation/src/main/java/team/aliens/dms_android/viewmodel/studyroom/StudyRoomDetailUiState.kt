package team.aliens.dms_android.viewmodel.studyroom

import team.aliens.dms_android._base.BaseUiState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity

data class StudyRoomDetailUiState(
    var studyRoomId: String = "",
    var currentSeat: MutableEventFlow<String> = MutableEventFlow(),
    var startAt: String = "",
    var endAt: String? = null,
    var errorMessage: MutableEventFlow<String> = MutableEventFlow(),
    var studyRoomDetails: StudyRoomDetailEntity = StudyRoomDetailEntity(
        floor = 0,
        name = "",
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
