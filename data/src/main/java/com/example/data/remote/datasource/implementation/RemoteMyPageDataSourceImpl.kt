package com.example.data.remote.datasource.implementation

import com.example.data.remote.api.MyPageApi
import com.example.data.remote.datasource.declaration.RemoteMyPageDataSource
import com.example.data.remote.response.mypage.FetchMyPageResponse
import com.example.data.remote.response.mypage.FetchPointListResponse
import com.example.data.util.HttpHandler
import com.example.domain.enums.PointType
import javax.inject.Inject

class RemoteMyPageDataSourceImpl @Inject constructor(
    private val myPageApi: MyPageApi
): RemoteMyPageDataSource {

    override suspend fun fetchMyPage() =
        HttpHandler<FetchMyPageResponse>()
            .httpRequest { myPageApi.fetchMyPage() }
            .sendRequest()

    override suspend fun    fetchPointList(pointType: PointType) =
        HttpHandler<FetchPointListResponse>()
            .httpRequest { myPageApi.fetchPoint(pointType) }
            .sendRequest()
}
