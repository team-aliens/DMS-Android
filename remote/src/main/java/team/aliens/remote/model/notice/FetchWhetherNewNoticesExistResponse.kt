package team.aliens.remote.model.notice

import com.google.gson.annotations.SerializedName

data class FetchWhetherNewNoticesExistResponse(
    @SerializedName("whether_new_notices") val newNotices: Boolean,
)
