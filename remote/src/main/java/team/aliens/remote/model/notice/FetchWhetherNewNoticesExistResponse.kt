package team.aliens.remote.model.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.notice.FetchWhetherNewNoticesExistOutput

data class FetchWhetherNewNoticesExistResponse(
    @SerializedName("whether_new_notices") val newNotices: Boolean,
)

internal fun FetchWhetherNewNoticesExistResponse.toDomain(): FetchWhetherNewNoticesExistOutput {
    return FetchWhetherNewNoticesExistOutput(
        newNoticesExists = this.newNotices,
    )
}
