package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.response.mypage.FetchMyPageResponse
import team.aliens.data.remote.response.mypage.FetchPointListResponse
import team.aliens.domain.enums.PointType

interface RemoteMyPageDataSource {

    suspend fun fetchMyPage(): FetchMyPageResponse

    suspend fun fetchPointList(pointType: PointType): FetchPointListResponse
}
