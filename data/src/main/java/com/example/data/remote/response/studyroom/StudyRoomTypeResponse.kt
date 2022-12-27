package com.example.data.remote.response.studyroom

import com.google.gson.annotations.SerializedName

data class StudyRoomTypeResponse(
    @SerializedName("types") val types: List<Type>,
) {
    data class Type(
        @SerializedName("color") val color: String,
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
    )
}