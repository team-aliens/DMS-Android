package team.aliens.dms_android.network.model.student

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.student.ExamineStudentNumberOutput

data class ExamineStudentNumberResponse(
    @SerializedName("name") val name: String,
)

internal fun ExamineStudentNumberResponse.toDomain(): ExamineStudentNumberOutput {
    return ExamineStudentNumberOutput(
        name = this.name,
    )
}
