package com.example.domain.entity.studyroom

data class StudyRoomListEntity(
    val studyRooms: List<StudyRoom>
){
    data class StudyRoom(
        val availableGrade: String,
        val availableSex: String,
        val floor: Int,
        val id: String,
        val inUseHeadcount: Int,
        val isMine: Boolean,
        val name: String,
        val totalAvailableSeat: Int,
        val studyRoomSex: String,
    )
}