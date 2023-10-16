package team.aliens.dms.android.network._legacy.model.notice

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.domain.model.notice.FetchWhetherNewNoticesExistOutput

data class FetchWhetherNewNoticesExistResponse(
    @SerializedName("whether_new_notices") val newNotices: Boolean,
)

internal fun FetchWhetherNewNoticesExistResponse.toDomain(): FetchWhetherNewNoticesExistOutput {
    return FetchWhetherNewNoticesExistOutput(
        newNotices = this.newNotices,
    )
}