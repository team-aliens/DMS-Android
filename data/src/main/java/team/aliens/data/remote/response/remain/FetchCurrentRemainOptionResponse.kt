package team.aliens.data.remote.response.remain

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.remain.CurrentRemainOptionEntity

data class FetchCurrentRemainOptionResponse(
    @SerializedName("title") val title: String,
)

fun FetchCurrentRemainOptionResponse.toEntity(): CurrentRemainOptionEntity {
    return CurrentRemainOptionEntity(
        title = this.title,
    )
}
