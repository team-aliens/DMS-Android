package team.aliens.remote.model.school

import com.google.gson.annotations.SerializedName

data class FetchSchoolVerificationQuestionResponse(
    @SerializedName("question") val question: String,
)
