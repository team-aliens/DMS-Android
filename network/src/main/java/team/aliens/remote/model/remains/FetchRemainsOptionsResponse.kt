package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput
import java.util.*

data class FetchRemainsOptionsResponse(
    @SerializedName("remain_options") val remainsOptionRespons: List<RemainsOptionResponse>,
) {
    data class RemainsOptionResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("is_applied") val applied: Boolean,
    )
}

internal fun FetchRemainsOptionsResponse.toDomain(): FetchRemainsOptionsOutput {
    return FetchRemainsOptionsOutput(
        remainOptions = this.remainsOptionRespons.toDomain(),
    )
}

internal fun FetchRemainsOptionsResponse.RemainsOptionResponse.toDomain(): FetchRemainsOptionsOutput.RemainsOptionInformation {
    return FetchRemainsOptionsOutput.RemainsOptionInformation(
        id = this.id,
        title = this.title,
        description = this.description,
        applied = this.applied,
    )
}

internal fun List<FetchRemainsOptionsResponse.RemainsOptionResponse>.toDomain(): List<FetchRemainsOptionsOutput.RemainsOptionInformation> {
    return this.map(FetchRemainsOptionsResponse.RemainsOptionResponse::toDomain)
}
