package team.aliens.dms.android.network.school.model

import com.google.gson.annotations.SerializedName

data class FetchSchoolVerificationQuestionResponse(
    @SerializedName("question") val question: String,
)
