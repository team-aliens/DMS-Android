package com.example.domain.entity.studyroom

data class StudyRoomTypeEntity(
    val types: List<Type>,
) {
    data class Type(
        val color: String,
        val id: String,
        val name: String,
    )
}