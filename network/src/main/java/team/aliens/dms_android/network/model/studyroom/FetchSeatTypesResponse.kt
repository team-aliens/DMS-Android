package team.aliens.dms_android.network.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model.studyroom.FetchSeatTypesOutput
import java.util.*

data class FetchSeatTypesResponse(
    @SerializedName("types") val types: List<SeatTypeResponse>,
) {
    data class SeatTypeResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: String,
    )
}

internal fun FetchSeatTypesResponse.toDomain(): FetchSeatTypesOutput {
    return FetchSeatTypesOutput(
        types = this.types.toDomain(),
    )
}

internal fun FetchSeatTypesResponse.SeatTypeResponse.toDomain(): FetchSeatTypesOutput.SeatTypeInformation {
    return FetchSeatTypesOutput.SeatTypeInformation(
        id = this.id,
        name = this.name,
        color = this.color,
    )
}

internal fun List<FetchSeatTypesResponse.SeatTypeResponse>.toDomain(): List<FetchSeatTypesOutput.SeatTypeInformation> {
    return this.map(FetchSeatTypesResponse.SeatTypeResponse::toDomain)
}
