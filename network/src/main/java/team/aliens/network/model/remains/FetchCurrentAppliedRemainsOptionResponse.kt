package team.aliens.network.model.remains

import com.google.gson.annotations.SerializedName
import java.util.UUID
import team.aliens.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput

data class FetchCurrentAppliedRemainsOptionResponse(
    @SerializedName("id") val appliedRemainsOptionId: UUID,
    @SerializedName("title") val title: String,
)

internal fun FetchCurrentAppliedRemainsOptionResponse.toDomain(): FetchCurrentAppliedRemainsOptionOutput {
    return FetchCurrentAppliedRemainsOptionOutput(
        appliedRemainsOptionId = this.appliedRemainsOptionId,
        title = this.title,
    )
}
