package team.aliens.remote.model.school

import com.google.gson.annotations.SerializedName
import java.util.*

data class ExamineSchoolVerificationCodeResponse(
    @SerializedName("school_id") val schoolId: UUID,
)
