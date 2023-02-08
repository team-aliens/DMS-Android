package team.aliens.data.remote.response.schools

import com.google.gson.annotations.SerializedName

data class SchoolConfirmQuestionResponse(
    @SerializedName("question") val question: String
)