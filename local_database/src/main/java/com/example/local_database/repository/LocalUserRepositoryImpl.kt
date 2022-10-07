package com.example.local_database.repository

import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.UserVisibleParam
import com.example.local_database.param.toEntity
import com.example.local_domain.entity.UserVisibleLocalEntity
import com.example.local_domain.entity.repository.LocalUserRepository
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource
): LocalUserRepository {
    override suspend fun setUserVisible(userVisibleLocalEntity: UserVisibleLocalEntity) {
        localUserDataSource.setUserVisibleInform(userVisibleLocalEntity.toParam())
    }

    override suspend fun fetchUserVisible(): UserVisibleLocalEntity =
        localUserDataSource.fetchUserVisibleInform().toEntity()

    override suspend fun clearUserVisible() {
        localUserDataSource.clearUserVisible()
    }

    private fun UserVisibleLocalEntity.toParam() =
        UserVisibleParam(
            surveyBoolean = surveyBoolean,
            noticeBoolean = noticeBoolean,
            myPageBoolean = myPageBoolean,
            recentRoomBoolean = recentRoomBoolean,
        )
}