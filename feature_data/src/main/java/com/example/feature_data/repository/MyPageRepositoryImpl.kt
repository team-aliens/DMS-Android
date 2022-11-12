package com.example.feature_data.repository

import com.example.feature_data.remote.datasource.declaration.RemoteMyPageDataSource
import com.example.feature_data.remote.response.mypage.toEntity
import com.example.feature_data.util.OfflineCacheUtil
import com.example.feature_domain.entity.mypage.MyPageEntity
import com.example.feature_domain.entity.mypage.PointListEntity
import com.example.feature_domain.enums.PointType
import com.example.feature_domain.repository.MyPageRepository
import com.example.local_database.datasource.declaration.LocalMyPageDataSource
import com.example.local_database.entity.mypage.PointListRoomEntity
import com.example.local_database.param.mypage.MyPageLocalParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val remoteMyPageDataSource: RemoteMyPageDataSource,
    private val localMyPageDataSource: LocalMyPageDataSource,
) : MyPageRepository {

    override suspend fun fetchMyPage(): Flow<MyPageEntity> =
        OfflineCacheUtil<MyPageEntity>()
            .remoteData { remoteMyPageDataSource.fetchMyPage().toEntity() }
            .doOnNeedRefresh { localMyPageDataSource.saveMyPage(it.toDbEntity()) }
            .createFlow()

    override suspend fun fetchPointList(pointType: PointType): Flow<PointListEntity> =
        OfflineCacheUtil<PointListEntity>()
            .remoteData { remoteMyPageDataSource.fetchPointList(pointType).toEntity() }
            .doOnNeedRefresh {
                localMyPageDataSource.savePointList(it.toDbEntity())
                localMyPageDataSource.saveTotalPoint(it.totalPoint)
            }
            .createFlow()
}

fun MyPageEntity.toDbEntity() =
    MyPageLocalParam(
        schoolName = schoolName,
        name = name,
        gcn = gcn,
        profileImageUrl = profileImageUrl,
        bonusPoint = bonusPoint,
        minusPoint = minusPoint,
        phrase = phrase,
    )

fun PointListEntity.PointValue.toDbEntity() =
    PointListRoomEntity(
        pointId = pointId,
        date = date,
        pointLocalType = pointType.toString(),
        name = name,
        score = score,
    )

fun PointListEntity.toDbEntity() =
    pointValue.map { it.toDbEntity() }

