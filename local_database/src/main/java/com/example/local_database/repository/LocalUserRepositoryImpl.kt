package com.example.local_database.repository

import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.UserVisibleParam
import com.example.local_domain.entity.UserVisibleLocalEntity.FeaturesParam
import com.example.local_database.param.toDbEntity
import com.example.local_domain.entity.repository.LocalUserRepository
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
): LocalUserRepository {
    override suspend fun setUserVisible(featuresParam: FeaturesParam) {
        localUserDataSource.setUserVisibleInform(featuresParam.toParam())
    }

    override suspend fun fetchUserVisible(): FeaturesParam =
        localUserDataSource.fetchUserVisibleInform().toDbEntity()

    private fun FeaturesParam.toParam() =
        UserVisibleParam.FeaturesParam(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
        )
}
