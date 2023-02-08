package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.MyPageApi
import team.aliens.data.remote.datasource.declaration.RemoteMyPageDataSource
import team.aliens.data.util.sendHttpRequest
import team.aliens.domain.enums.PointType
import javax.inject.Inject

class RemoteMyPageDataSourceImpl @Inject constructor(
    private val myPageApi: MyPageApi,
) : RemoteMyPageDataSource {

    override suspend fun fetchMyPage() =
        sendHttpRequest(httpRequest = { myPageApi.fetchMyPage() })

    override suspend fun fetchPointList(pointType: PointType) =
        sendHttpRequest(httpRequest = { myPageApi.fetchPoint(pointType) })
}
