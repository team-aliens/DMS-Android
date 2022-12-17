package com.example.data.remote.api

import com.example.data.remote.response.mypage.FetchMyPageResponse
import com.example.data.remote.response.mypage.FetchPointListResponse
import com.example.data.remote.url.DmsUrl
import com.example.domain.enums.PointType
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageApi {

    @GET(DmsUrl.MyPage.MyPage)
    suspend fun fetchMyPage(): FetchMyPageResponse

    @GET(DmsUrl.MyPage.Point)
    suspend fun fetchPoint(
        @Query("type") pointType: PointType
    ): FetchPointListResponse
}
