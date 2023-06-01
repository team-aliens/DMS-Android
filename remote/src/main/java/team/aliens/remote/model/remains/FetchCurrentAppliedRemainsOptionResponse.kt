package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName
import java.util.UUID
import team.aliens.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput

data class FetchCurrentAppliedRemainsOptionResponse(
    @SerializedName("remain_option_id") val id: UUID,
    @SerializedName("title") val title: String,
)

internal fun FetchCurrentAppliedRemainsOptionResponse.toDomain(): FetchCurrentAppliedRemainsOptionOutput {
    return FetchCurrentAppliedRemainsOptionOutput(
        id = this.id,
        title = this.title,
    )
}
