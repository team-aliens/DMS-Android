package com.example.local_database.repository.mypage

import com.example.local_database.datasource.declaration.LocalMyPageDataSource
import com.example.local_database.datasource.implementation.LocalMyPageDataSourceImpl
import com.example.local_database.entity.mypage.toEntity
import com.example.local_database.param.mypage.toParam
import com.example.local_domain.entity.mypage.PointListValueEntity
import com.example.local_domain.param.MyPageParam
import com.example.local_domain.repository.mypage.LocalMyPageRepository
import javax.inject.Inject

class LocalMyPageRepositoryImpl @Inject constructor(
    private val localMyPageDataSource: LocalMyPageDataSource
): LocalMyPageRepository {

    override suspend fun fetchPointList(pointLocalType: String): List<PointListValueEntity> =
        localMyPageDataSource.fetchPointList(pointLocalType).map { it.toEntity() }

    override suspend fun fetchMyPage(): MyPageParam =
        localMyPageDataSource.fetchMyPage().toParam()

    override suspend fun fetchTotalPoint(): Int =
        localMyPageDataSource.fetchTotalPoint()
}
