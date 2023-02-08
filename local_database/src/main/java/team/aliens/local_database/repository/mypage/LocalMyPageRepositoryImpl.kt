package team.aliens.local_database.repository.mypage

import team.aliens.local_database.datasource.declaration.LocalMyPageDataSource
import team.aliens.local_domain.entity.mypage.PointListValueEntity
import team.aliens.local_domain.param.MyPageParam
import team.aliens.local_domain.repository.mypage.LocalMyPageRepository
import javax.inject.Inject

class LocalMyPageRepositoryImpl @Inject constructor(
    private val localMyPageDataSource: LocalMyPageDataSource,
) : LocalMyPageRepository {

    override suspend fun fetchPointList(pointLocalType: String): List<PointListValueEntity> =
        localMyPageDataSource.fetchPointList(pointLocalType).map { it.toEntity() }

    override suspend fun fetchMyPage(): MyPageParam = localMyPageDataSource.fetchMyPage().toParam()

    override suspend fun fetchTotalPoint(): Int = localMyPageDataSource.fetchTotalPoint()
}
