package team.aliens.remote.model.studyroom

import com.google.gson.annotations.SerializedName
import java.util.*

data class FetchSeatTypesResponse(
    @SerializedName("types") val types: List<Type>,
) {
    data class Type(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("color") val color: String,
    )
}
