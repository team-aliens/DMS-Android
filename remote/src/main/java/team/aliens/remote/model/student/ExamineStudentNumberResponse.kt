package team.aliens.remote.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.student.ExamineStudentNumberOutput

data class ExamineStudentNumberResponse(
    @SerializedName("name") val name: String,
)

internal fun ExamineStudentNumberResponse.toDomain(): ExamineStudentNumberOutput {
    return ExamineStudentNumberOutput(
        name = this.name,
    )
}
