package team.aliens.dms.android.network.student.model

import com.google.gson.annotations.SerializedName

data class ExamineStudentNumberResponse(
    @SerializedName("name") val studentName: String,
)
