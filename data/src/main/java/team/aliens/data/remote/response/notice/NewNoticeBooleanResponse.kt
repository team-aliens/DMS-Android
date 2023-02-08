package team.aliens.data.remote.response.notice

import com.example.domain.entity.notice.NewNoticeBooleanEntity
import com.google.gson.annotations.SerializedName

data class NewNoticeBooleanResponse(
    @SerializedName("whether_new_notices") val newNoticeBoolean: Boolean,
)

fun NewNoticeBooleanResponse.toEntity() =
    NewNoticeBooleanEntity(
        noticeBoolean = newNoticeBoolean
    )
