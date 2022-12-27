package com.example.data.remote.response.studyroom

import com.google.gson.annotations.SerializedName

data class StudyRoomListResponse(
    @SerializedName("study_rooms") val studyRooms: List<StudyRoom>,
) {
    data class StudyRoom(
        @SerializedName("available_grade") val availableGrade: Int,
        @SerializedName("available_sex") val availableSex: String,
        @SerializedName("floor") val floor: Int,
        @SerializedName("id") val id: String,
        @SerializedName("in_use_head_count") val inUseHeadcount: Int,
        @SerializedName("in_mine") val isMine: Boolean,
        @SerializedName("name") val name: String,
        @SerializedName("total_available_seat") val totalAvailableSeat: Int,
    )
}