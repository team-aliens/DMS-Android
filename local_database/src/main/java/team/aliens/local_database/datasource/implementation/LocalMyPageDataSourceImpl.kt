package team.aliens.local_database.datasource.implementation

import team.aliens.local_database.dao.PointDao
import team.aliens.local_database.datasource.declaration.LocalMyPageDataSource
import team.aliens.local_database.entity.mypage.PointListRoomEntity
import team.aliens.local_database.param.mypage.MyPageLocalParam
import team.aliens.local_database.storage.declaration.MyPageDataStorage
import javax.inject.Inject

class LocalMyPageDataSourceImpl @Inject constructor(
    private val pointDao: PointDao,
    private val myPageDataStorage: MyPageDataStorage,
) : LocalMyPageDataSource {
    override suspend fun saveMyPage(myPageLocalParam: MyPageLocalParam) {
        myPageDataStorage.saveMyPage(myPageLocalParam)
    }

    override suspend fun fetchMyPage(): MyPageLocalParam {
        myPageDataStorage.apply {
            return MyPageLocalParam(
                fetchSchoolName(),
                fetchName(),
                fetchGcn(),
                fetchProfile(),
                fetchBonusPoint(),
                fetchMinusPoint(),
                fetchPhrase(),
            )
        }
    }

    override suspend fun savePointList(
        pointLocalValue: List<PointListRoomEntity>
    ) {
        pointDao.savePointList(pointLocalValue)
    }

    override suspend fun fetchPointList(pointLocalType: String): List<PointListRoomEntity> =
        pointDao.fetchPointList(pointLocalType)

    override suspend fun saveTotalPoint(totalPoint: Int) {
        myPageDataStorage.saveTotalPoint(totalPoint)
    }

    override suspend fun fetchTotalPoint(): Int =
        myPageDataStorage.fetchTotalPoint()
}
