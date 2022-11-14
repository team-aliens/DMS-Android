package com.example.feature_domain.repository

import com.example.feature_domain.entity.mypage.MyPageEntity
import com.example.feature_domain.entity.mypage.PointListEntity
import com.example.feature_domain.enums.PointType
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {

    suspend fun fetchMyPage(): Flow<MyPageEntity>

    suspend fun fetchPointList(pointType: PointType): Flow<PointListEntity>
}
