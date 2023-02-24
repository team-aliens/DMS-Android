package team.aliens.data.remote.response.remain

import com.google.gson.annotations.SerializedName
import team.aliens.data.remote.response.remain.FetchRemainOptionsResponse.RemainOptionResponse
import team.aliens.domain.entity.remain.RemainOptionsEntity
import team.aliens.domain.entity.remain.RemainOptionsEntity.RemainOptionEntity
import java.util.*

data class FetchRemainOptionsResponse(
    @SerializedName("remain_options") val remainOptionEntities: List<RemainOptionResponse>,
) {
    data class RemainOptionResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
    )
}

fun RemainOptionResponse.toEntity(): RemainOptionEntity {
    return RemainOptionEntity(
        id = this.id,
        title = this.title,
        description = this.description,
    )
}

fun FetchRemainOptionsResponse.toEntity(): RemainOptionsEntity {
    return RemainOptionsEntity(
        remainOptionEntities = this.remainOptionEntities.map {
            it.toEntity()
        },
    )
}
