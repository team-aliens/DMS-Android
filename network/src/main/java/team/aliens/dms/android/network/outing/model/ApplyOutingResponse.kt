package team.aliens.dms.android.network.outing.model

import com.google.gson.annotations.SerializedName

data class ApplyOutingResponse(
    @SerializedName("outing_application_id") val applicationId: String,
)
