package com.example.dms_android.feature.studyroom

import com.example.dms_android.base.MviState

data class StudyRoomState(
    var a: String,
    var studyRoomDetailEntity: StudyRoomDetailEntity = StudyRoomDetailEntity(),
    var seats: List<Seat> = listOf<Seat>()
): MviState {
    companion object {
        fun initial() =
            StudyRoomState(
                a = ""
            )
    }
}

data class Seat(
    var id: String = "",
    var widthLocation: Int = 0,
    var heightLocation: Int = 0,
    var number: Int? = 0,
    var type: StudyRoomDetailEntity.Type = StudyRoomDetailEntity.Type(),
    var status: String = "",
    var isMine: Boolean? = false,
    var student: StudyRoomDetailEntity.Student = StudyRoomDetailEntity.Student(),
)

data class StudyRoomDetailEntity(
    var floor: Int = 0,
    var name: String = "",
    var totalAvailableSeat: Int = 0,
    var inUseHeadCount: Int = 0,
    var availableSex: String = "",
    var availableGrade: Int = 0,
    var eastDescription: String = "",
    var westDescription: String = "",
    var southDescription: String = "",
    var northDescription: String = "",
    var totalWidthSize: Int = 0,
    var totalHeightSize: Int = 0,
) {

    data class Type(
        var id: String = "",
        var name: String = "",
        var color: String = "",
    )

    data class Student(
        var id: String = "",
        var name: String = "",
    )
}
