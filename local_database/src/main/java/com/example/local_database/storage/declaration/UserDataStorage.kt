package com.example.local_database.storage.declaration

import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam

interface UserDataStorage {

    fun setPersonalKey(personalKeyParam: UserPersonalKeyParam)

    fun fetchAccessToken(): String
    fun fetchAccessTokenExpiredAt(): String
    fun fetchRefreshToken(): String
    fun fetchRefreshTokenExpiredAt(): String

    fun setUserVisible(featuresParam: FeaturesParam)

    fun fetchMealServiceBoolean(): Boolean
    fun fetchNoticeServiceBoolean(): Boolean
    fun fetchPointServiceBoolean(): Boolean
}
