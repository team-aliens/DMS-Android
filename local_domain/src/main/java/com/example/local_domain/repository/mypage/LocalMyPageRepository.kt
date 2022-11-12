package com.example.local_domain.repository.mypage

import com.example.local_domain.entity.mypage.PointListEntity
import com.example.local_domain.param.MyPageParam

interface LocalMyPageRepository {

    suspend fun fetchPointList(pointLocalType: String): List<PointListEntity>

    suspend fun fetchMyPage(): MyPageParam

    suspend fun fetchTotalPoint(): Int
}