package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.UUID

data class ApplyOutingRequest(
    @SerializedName("outing_date") val date: LocalDate,
    @SerializedName("outing_time") val startTime: LocalDateTime,
    @SerializedName("arrival_time") val endTime: LocalDateTime,
    @SerializedName("outing_type_title") val type: String,
    @SerializedName("reason") val reason: String?,
    @SerializedName("companion_ids") val followerIds: List<UUID>,
)
