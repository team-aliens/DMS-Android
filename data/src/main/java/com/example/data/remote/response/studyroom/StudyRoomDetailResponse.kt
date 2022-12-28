package com.example.data.remote.response.studyroom

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class StudyRoomDetailResponse(
    @SerializedName("floor") val floor: Int,
    @SerializedName("name") val name: String,
    @SerializedName("total_available_seat") val totalAvailableSeat: Int,
    @SerializedName("in_use_head_count") val inUseHeadCount: Int,
    @SerializedName("available_sex") val availableSex: String,
    @SerializedName("available_grade") val availableGrade: Int,
    @SerializedName("east_description") val eastDescription: String,
    @SerializedName("west_description") val westDescription: String,
    @SerializedName("south_description") val southDescription: String,
    @SerializedName("north_description") val northDescription: String,
    @SerializedName("total_width_size") val totalWidthSize: Int,
    @SerializedName("total_height_size") val totalHeightSize: Int,
    @SerializedName("seats") val seats: List<Seat>,
) {
    data class Seat(
        @SerializedName("id") val id: UUID,
        @SerializedName("width_location") val widthLocation: Int,
        @SerializedName("height_location") val heightLocation: Int,
        @SerializedName("number") val number: Int?,
        @SerializedName("type") val type: Type?,
        @SerializedName("status") val status: String,
        @SerializedName("is_mine") val isMine: Boolean?,
        @SerializedName("student") val student: Student?,
    )

    data class Type(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: String,
    )

    data class Student(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
    )
}
