package team.aliens.dms.android.network.latestudy.model

import com.google.gson.annotations.SerializedName

data class StudyApplicationStatusResponse(
    val status: String,
    val dates: List<String>? = null,
    @SerializedName("start_date")
    val startDate: String? = null,
    @SerializedName("end_date")
    val endDate: String? = null,
    @SerializedName(value = "teacher_name", alternate = ["teacherName"])
    val teacherName: String? = null,
    @SerializedName(value = "type_name", alternate = ["typeName"])
    val typeName: String? = null,
    val reason: String? = null,
) {
    val applicationDates: List<String>
        get() = dates ?: listOfNotNull(startDate, endDate)
}
