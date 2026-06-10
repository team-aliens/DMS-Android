package team.aliens.dms.android.network.latestudy.model

import com.google.gson.annotations.SerializedName

data class StudyApplicationStatusResponse(
    val status: String,

    @SerializedName("start_date")
    val startDate: String?,

    @SerializedName("end_date")
    val endDate: String?,
)
