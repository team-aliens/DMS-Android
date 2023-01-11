package com.example.dms_android.feature.studyroom

import com.example.dms_android.base.MviState
import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.SeatTypeEntity

data class StudyRoomState(
    val currentSeat: String?,
    var startAt: String,
    var endAt: String,
    var seatTypeListEntity: SeatTypeEntity,
    val roomDetail: StudyRoomDetailEntity,
) : MviState {

    companion object {
        fun initial() =
            StudyRoomState(
                startAt = "",
                endAt = "",
                currentSeat = null,
                roomDetail = StudyRoomDetailEntity(
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
                                name = ""
                            )
                        )
                    )
                ),
                seatTypeListEntity = SeatTypeEntity(
                    types = listOf(
                        SeatTypeEntity.Type(
                            id = "",
                            name = "",
                            color = "",
                        )
                    )
                )
            )
    }
}