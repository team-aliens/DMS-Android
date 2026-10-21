package team.aliens.dms.android.network.latestudy.model

import com.google.gson.annotations.SerializedName

data class SubmitLateStudyRequest(
    @SerializedName("teacher_id")
    val teacherId: String,
    @SerializedName("type_id")
    val typeId: String,
    val reason: String,
    val dates: List<String>,
)
