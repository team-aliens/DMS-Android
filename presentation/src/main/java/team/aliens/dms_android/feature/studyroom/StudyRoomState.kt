package team.aliens.dms_android.feature.studyroom

import team.aliens.dms_android.base.MviState
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity

data class StudyRoomState(
    val currentSeat: String?,
    var startAt: String,
    var endAt: String,
    val roomDetail: StudyRoomDetailEntity,
) : MviState {
    companion object {
        fun initial() = StudyRoomState(startAt = "",
            endAt = "",
            currentSeat = null,
            roomDetail = StudyRoomDetailEntity(floor = 0,
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
                seats = listOf(StudyRoomDetailEntity.Seat(id = "",
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
                    student = StudyRoomDetailEntity.Student(id = "", name = "")))))
    }
}