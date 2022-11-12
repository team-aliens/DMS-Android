package com.example.feature_data.remote.api

import com.example.feature_data.remote.response.mypage.FetchMyPageResponse
import com.example.feature_data.remote.response.mypage.FetchPointListResponse
import com.example.feature_data.remote.url.DmsUrl
import com.example.feature_domain.enums.PointType
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageApi {

    @GET(DmsUrl.MyPage.MyPage)
    suspend fun fetchMyPage(): FetchMyPageResponse

    @GET(DmsUrl.points)
    suspend fun fetchPoint(
        @Query("type") pointType: PointType
    ): FetchPointListResponse
}