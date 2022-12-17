package com.example.domain.repository

import com.example.domain.entity.mypage.MyPageEntity
import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {

    suspend fun fetchMyPage(): Flow<MyPageEntity>

    suspend fun fetchPointList(pointType: PointType): Flow<PointListEntity>
}
