package com.example.local_database.datasource.implementation

import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.UserVisibleParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_domain.entity.UserVisibleLocalEntity
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val userDataStorage: UserDataStorage
): LocalUserDataSource {
    override suspend fun setUserVisibleInform(userVisibleParam: UserVisibleParam) {
        userDataStorage.setUserVisible(userVisibleParam)
    }

    override suspend fun fetchUserVisibleInform(): UserVisibleParam {
        userDataStorage.apply {
            return UserVisibleParam(
                fetchSurveyBoolean(),
                fetchNoticeBoolean(),
                fetchMyPageBoolean(),
                fetchRecentRoomBoolean(),
            )
        }
    }
}