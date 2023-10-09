package team.aliens.dms_android.network.model.school

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.school.FetchSchoolVerificationQuestionOutput

data class FetchSchoolVerificationQuestionResponse(
    @SerializedName("question") val question: String,
)

internal fun FetchSchoolVerificationQuestionResponse.toDomain(): FetchSchoolVerificationQuestionOutput {
    return FetchSchoolVerificationQuestionOutput(
        question = this.question,
    )
}
