package team.aliens.dms_android.feature.feature.studyroom

import team.aliens.dms_android.feature.base._MviState
import team.aliens.domain.model._common.Sex
import team.aliens.domain.model.studyroom.FetchStudyRoomDetailsOutput

data class StudyRoomState(
    val currentSeat: String?,
    var startAt: String,
    var endAt: String,
    val roomDetail: FetchStudyRoomDetailsOutput,
) : _MviState {
    companion object {
        fun getDefaultInstance() = StudyRoomState(
            startAt = "",
            endAt = "",
            currentSeat = null,
            roomDetail = FetchStudyRoomDetailsOutput(
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
                totalWidthSize = 2,
                totalHeightSize = 2,
                seats = emptyList(),
            ),
        )
    }
}