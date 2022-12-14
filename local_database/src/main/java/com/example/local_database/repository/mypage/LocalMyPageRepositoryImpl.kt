package com.example.local_database.repository.mypage

import com.example.local_database.datasource.implementation.LocalMyPageDataSourceImpl
import com.example.local_database.entity.mypage.toEntity
import com.example.local_database.param.mypage.toParam
import com.example.local_domain.entity.mypage.PointListValueEntity
import com.example.local_domain.param.MyPageParam
import com.example.local_domain.repository.mypage.LocalMyPageRepository
import javax.inject.Inject

class LocalMyPageRepositoryImpl @Inject constructor(
    private val localMyPageDataSourceImpl: LocalMyPageDataSourceImpl
): LocalMyPageRepository {

    override suspend fun fetchPointList(pointLocalType: String): List<PointListValueEntity> =
        localMyPageDataSourceImpl.fetchPointList(pointLocalType).map { it.toEntity() }

    override suspend fun fetchMyPage(): MyPageParam =
        localMyPageDataSourceImpl.fetchMyPage().toParam()

    override suspend fun fetchTotalPoint(): Int =
        localMyPageDataSourceImpl.fetchTotalPoint()
}
