package team.aliens.dms.android.network.notice.model

import com.google.gson.annotations.SerializedName

data class FetchWhetherNewNoticesExistResponse(
    @SerializedName("whether_new_notices") val whetherNewNotices: Boolean,
)
