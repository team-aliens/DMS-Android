package team.aliens.data.repository

import com.example.data.remote.datasource.declaration.RemoteMyPageDataSource
import com.example.data.remote.response.mypage.toEntity
import com.example.domain.entity.mypage.MyPageEntity
import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType
import com.example.domain.repository.MyPageRepository
import com.example.local_database.datasource.declaration.LocalMyPageDataSource
import com.example.local_database.param.mypage.MyPageLocalParam
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

fun MyPageEntity.toDbEntity() =
    MyPageLocalParam(
        schoolName = schoolName,
        name = name,
        gcn = gcn,
        profileImageUrl = profileImageUrl,
        bonusPoint = bonusPoint,
        minusPoint = minusPoint,
        phrase = phrase,
    )
