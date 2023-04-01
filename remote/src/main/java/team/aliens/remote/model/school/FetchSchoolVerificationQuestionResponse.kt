package team.aliens.remote.model.school

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput

data class FetchSchoolVerificationQuestionResponse(
    @SerializedName("question") val question: String,
)

internal fun FetchSchoolVerificationQuestionResponse.toDomain(): FetchSchoolVerificationQuestionOutput {
    return FetchSchoolVerificationQuestionOutput(
        question = this.question,
    )
}
