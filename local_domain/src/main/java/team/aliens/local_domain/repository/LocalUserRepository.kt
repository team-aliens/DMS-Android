package team.aliens.local_domain.repository

import team.aliens.local_domain.param.FeaturesVisibleParam

interface LocalUserRepository {

    suspend fun setUserVisible(featuresParam: FeaturesVisibleParam)
    suspend fun fetchUserVisible(): FeaturesVisibleParam
}