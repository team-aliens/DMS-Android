package team.aliens.dms.android.network.model.school

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeOutput
import java.util.*

data class ExamineSchoolVerificationCodeResponse(
    @SerializedName("school_id") val schoolId: UUID,
)

internal fun ExamineSchoolVerificationCodeResponse.toDomain(): ExamineSchoolVerificationCodeOutput {
    return ExamineSchoolVerificationCodeOutput(
        schoolId = this.schoolId,
    )
}
