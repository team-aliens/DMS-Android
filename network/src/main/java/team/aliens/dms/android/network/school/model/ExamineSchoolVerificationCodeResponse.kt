package team.aliens.dms.android.network.school.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ExamineSchoolVerificationCodeResponse(
    @SerializedName("school_id") val schoolId: UUID,
)
