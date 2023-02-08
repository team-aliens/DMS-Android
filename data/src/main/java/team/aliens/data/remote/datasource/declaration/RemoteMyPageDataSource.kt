package team.aliens.data.remote.datasource.declaration

import com.example.data.remote.response.mypage.FetchMyPageResponse
import com.example.data.remote.response.mypage.FetchPointListResponse
import com.example.domain.enums.PointType

interface RemoteMyPageDataSource {

    suspend fun fetchMyPage(): FetchMyPageResponse

    suspend fun fetchPointList(pointType: PointType): FetchPointListResponse
}
