package com.example.domain.entity.studyroom

import java.util.UUID

data class StudyRoomDetailEntity(
    val floor: Int,
    val name: String,
    val totalAvailableSeat: Int,
    val inUseHeadCount: Int,
    val availableSex: String,
    val availableGrade: Int,
    val eastDescription: String,
    val westDescription: String,
    val southDescription: String,
    val northDescription: String,
    val totalWidthSize: Int,
    val totalHeightSize: Int,
    val seats: Seat,
) {
    data class Seat(
        val id: UUID,
        val widthLocation: Int,
        val heightLocation: Int,
        val number: Int?,
        val type: Type?,
        val status: String,
        val isMine: Boolean?,
        val student: Student?,
    )

    data class Type(
        val id: UUID,
        val name: String,
        val color: String,
    )

    data class Student(
        val id: UUID,
        val name: String,
    )
}
