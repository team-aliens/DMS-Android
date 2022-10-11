package com.example.local_database.param

import com.example.local_domain.entity.UserVisibleLocalEntity

data class UserVisibleParam(
    val surveyBoolean: Boolean,
    val noticeBoolean: Boolean,
    val myPageBoolean: Boolean,
    val recentRoomBoolean: Boolean,
)

fun UserVisibleParam.toEntity() =
    UserVisibleLocalEntity(
        surveyBoolean = surveyBoolean,
        noticeBoolean = noticeBoolean,
        myPageBoolean = myPageBoolean,
        recentRoomBoolean = recentRoomBoolean,
    )