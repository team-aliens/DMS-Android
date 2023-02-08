package team.aliens.data.remote.response.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.notice.NewNoticeBooleanEntity

data class NewNoticeBooleanResponse(
    @SerializedName("whether_new_notices") val newNoticeBoolean: Boolean,
)

fun NewNoticeBooleanResponse.toEntity() = NewNoticeBooleanEntity(noticeBoolean = newNoticeBoolean)
