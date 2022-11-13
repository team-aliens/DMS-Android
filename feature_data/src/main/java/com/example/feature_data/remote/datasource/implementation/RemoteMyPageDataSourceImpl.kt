package com.example.feature_data.remote.datasource.implementation

import com.example.feature_data.remote.api.MyPageApi
import com.example.feature_data.remote.datasource.declaration.RemoteMyPageDataSource
import com.example.feature_data.remote.response.mypage.FetchMyPageResponse
import com.example.feature_data.remote.response.mypage.FetchPointListResponse
import com.example.feature_data.util.HttpHandler
import com.example.feature_domain.enums.PointType
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
