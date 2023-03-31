package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput

data class FetchCurrentAppliedRemainsOptionResponse(
    @SerializedName("title") val title: String,
)

internal fun FetchCurrentAppliedRemainsOptionResponse.toDomain(): FetchCurrentAppliedRemainsOptionOutput {
    return FetchCurrentAppliedRemainsOptionOutput(
        title = this.title,
    )
}
