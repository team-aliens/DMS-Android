package com.example.local_database.storage.declaration

import com.example.local_database.param.UserVisibleParam.FeaturesParam

interface UserDataStorage {

    fun setUserVisible(featuresParam: FeaturesParam)

    fun fetchMealServiceBoolean(): Boolean
    fun fetchNoticeServiceBoolean(): Boolean
    fun fetchPointServiceBoolean(): Boolean
}
