package com.example.feature_data.remote.datasource.declaration

import com.example.feature_data.remote.response.mypage.FetchMyPageResponse
import com.example.feature_data.remote.response.mypage.FetchPointListResponse
import com.example.feature_domain.enums.PointType

interface RemoteMyPageDataSource {

    suspend fun fetchMyPage(): FetchMyPageResponse

    suspend fun fetchPointList(pointType: PointType): FetchPointListResponse
}
