package team.aliens.dms.android.network.model.remains

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import java.util.UUID

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
