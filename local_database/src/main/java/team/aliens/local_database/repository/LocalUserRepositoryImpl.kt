package team.aliens.local_database.repository

import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.toDbEntity
import team.aliens.local_domain.param.FeaturesVisibleParam
import team.aliens.local_domain.repository.LocalUserRepository
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
) : LocalUserRepository {
    override suspend fun setUserVisible(featuresParam: FeaturesVisibleParam) {
        localUserDataSource.setUserVisibleInform(featuresParam.toParam())
    }

    override suspend fun fetchUserVisible(): FeaturesVisibleParam =
        localUserDataSource.fetchUserVisibleInform().toDbEntity()

    private fun FeaturesVisibleParam.toParam() = FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )
}
