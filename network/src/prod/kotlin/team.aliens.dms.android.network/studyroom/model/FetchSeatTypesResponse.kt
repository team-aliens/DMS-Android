package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchSeatTypesResponse(
    @SerializedName("types") val types: List<SeatTypeResponse>,
) {
    data class SeatTypeResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: String,
    )
}
