package com.example.local_database.datasource.implementation

import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.localutil.toLocalDateTime
import com.example.local_database.param.user.UserInfoParam
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
                fetchAccessTokenExpiredAt().toLocalDateTime(),
                fetchRefreshToken(),
                fetchRefreshTokenExpiredAt().toLocalDateTime(),
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

    override suspend fun setUserInfo(userInfoParam: UserInfoParam) {
        userDataStorage.setUserInfo(userInfoParam)
    }

    override suspend fun fetchUserInfo(): UserInfoParam {
        userDataStorage.apply {
            return UserInfoParam(
                fetchId(),
                fetchPassword(),
            )
        }
    }
}
