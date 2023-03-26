package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName

data class ExamineStudentNumberResponse(
    @SerializedName("name") val name: String,
)
