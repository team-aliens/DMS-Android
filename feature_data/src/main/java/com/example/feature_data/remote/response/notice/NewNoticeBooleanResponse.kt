package com.example.feature_data.remote.response.notice

import com.example.feature_domain.entity.notice.NewNoticeBooleanEntity
import com.google.gson.annotations.SerializedName

data class NewNoticeBooleanResponse(
    @SerializedName("whether_new_notices") val newNoticeBoolean: Boolean,
)

fun NewNoticeBooleanResponse.toEntity() =
    NewNoticeBooleanEntity(
        noticeBoolean = newNoticeBoolean
    )
