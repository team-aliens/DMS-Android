package com.example.local_database.storage.declaration

import com.example.local_database.param.UserVisibleParam

interface UserDataStorage {

    fun setUserVisible(userVisibleParam: UserVisibleParam)

    fun fetchSurveyBoolean(): Boolean
    fun fetchNoticeBoolean(): Boolean
    fun fetchMyPageBoolean(): Boolean
    fun fetchRecentRoomBoolean(): Boolean
}