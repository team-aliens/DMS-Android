package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ApplyOutingResponse(
    @SerializedName("outing_application_id") val applicationId: UUID,
)
