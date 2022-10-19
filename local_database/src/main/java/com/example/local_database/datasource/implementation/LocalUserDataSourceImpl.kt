package com.example.local_database.datasource.implementation

import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.UserVisibleParam.FeaturesParam
import com.example.local_database.storage.declaration.UserDataStorage
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val userDataStorage: UserDataStorage
): LocalUserDataSource {
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
