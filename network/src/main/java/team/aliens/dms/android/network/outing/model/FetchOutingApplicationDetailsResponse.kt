package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.util.UUID

data class FetchOutingApplicationDetailsResponse(
    @SerializedName("outing_time") val startTime: LocalDateTime,
    @SerializedName("arrival_time") val endTime: LocalDateTime,
    @SerializedName("outing_status") val status: String,
    @SerializedName("reason") val reason: String?,
    @SerializedName("outing_type") val type: String,
    @SerializedName("students") val student: List<StudentResponse>,
) {
    data class StudentResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("gcn") val gradeClassNumber: String,
        @SerializedName("room_number") val roomNumber: String,
    )
}
