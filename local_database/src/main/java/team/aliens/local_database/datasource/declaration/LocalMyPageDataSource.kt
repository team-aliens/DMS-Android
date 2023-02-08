package team.aliens.local_database.datasource.declaration

import com.example.local_database.entity.mypage.PointListRoomEntity
import com.example.local_database.param.mypage.MyPageLocalParam

interface LocalMyPageDataSource {

    suspend fun saveMyPage(myPageLocalParam: MyPageLocalParam)
    suspend fun fetchMyPage(): MyPageLocalParam

    suspend fun savePointList(pointListRoomEntity: List<PointListRoomEntity>)
    suspend fun fetchPointList(pointLocalType: String): List<PointListRoomEntity>

    suspend fun saveTotalPoint(totalPoint: Int)
    suspend fun fetchTotalPoint(): Int
}
