package team.aliens.data.remote.datasource.implementation

import com.example.data.remote.api.MyPageApi
import com.example.data.remote.datasource.declaration.RemoteMyPageDataSource
import com.example.data.util.sendHttpRequest
import com.example.domain.enums.PointType
import javax.inject.Inject

class RemoteMyPageDataSourceImpl @Inject constructor(
    private val myPageApi: MyPageApi
) : RemoteMyPageDataSource {

    override suspend fun fetchMyPage() =
        sendHttpRequest(httpRequest = { myPageApi.fetchMyPage() })

    override suspend fun fetchPointList(pointType: PointType) =
        sendHttpRequest(httpRequest = { myPageApi.fetchPoint(pointType) })
}
