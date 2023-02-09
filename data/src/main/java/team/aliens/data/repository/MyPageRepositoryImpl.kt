package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteMyPageDataSource
import team.aliens.data.remote.response.mypage.toEntity
import team.aliens.domain.entity.mypage.MyPageEntity
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType
import team.aliens.domain.repository.MyPageRepository
import team.aliens.local_database.datasource.declaration.LocalMyPageDataSource
import team.aliens.local_database.param.mypage.MyPageLocalParam
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val remoteMyPageDataSource: RemoteMyPageDataSource,
    private val localMyPageDataSource: LocalMyPageDataSource,
) : MyPageRepository {

    override suspend fun fetchMyPage(): MyPageEntity =
        remoteMyPageDataSource.fetchMyPage().toEntity()

    override suspend fun fetchPointList(pointType: PointType): PointListEntity =
        remoteMyPageDataSource.fetchPointList(pointType).toEntity()
}

fun MyPageEntity.toDbEntity() = MyPageLocalParam(
    schoolName = schoolName,
    name = name,
    gcn = gcn,
    profileImageUrl = profileImageUrl,
    bonusPoint = bonusPoint,
    minusPoint = minusPoint,
    phrase = phrase,
)
