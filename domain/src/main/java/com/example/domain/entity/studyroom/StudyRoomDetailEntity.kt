package com.example.domain.entity.studyroom

import java.util.UUID

data class StudyRoomDetailEntity(
    var floor: Int,
    var name: String,
    var totalAvailableSeat: Int,
    var inUseHeadCount: Int,
    var availableSex: String,
    var availableGrade: Int,
    var eastDescription: String,
    var westDescription: String,
    var southDescription: String,
    var northDescription: String,
    var totalWidthSize: Int,
    var totalHeightSize: Int,
    var studyRoomSex: String,
    var seats: List<Seat>,
) {
    data class Seat(
        var id: String,
        var widthLocation: Int,
        var heightLocation: Int,
        var number: Int?,
        var type: Type?,
        var status: String,
        var isMine: Boolean?,
        var student: Student?,
    )

    data class Type(
        var id: String,
        var name: String,
        var color: String,
    )

    data class Student(
        var id: String,
        var name: String,
    )
}
