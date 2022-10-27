package com.example.local_database.datasource.implementation

import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import com.example.local_database.storage.declaration.UserDataStorage
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val userDataStorage: UserDataStorage
): LocalUserDataSource {

    override suspend fun setPersonalKey(personalKeyParam: UserPersonalKeyParam) {
        userDataStorage.setPersonalKey(personalKeyParam)
    }

    override suspend fun fetchPersonalKey(): UserPersonalKeyParam {
        userDataStorage.apply {
            return UserPersonalKeyParam(
                fetchAccessToken(),
                fetchExpiredAt(),
                fetchRefreshToken()
            )
        }
    }

    override suspend fun setUserVisibleInform(featuresParam: FeaturesParam) {
        userDataStorage.setUserVisible(featuresParam)
    }

    override suspend fun fetchUserVisibleInform(): FeaturesParam {
        userDataStorage.apply {
            return FeaturesParam(
                fetchMealServiceBoolean(),
                fetchNoticeServiceBoolean(),
                fetchPointServiceBoolean(),
            )
        }
    }
}
