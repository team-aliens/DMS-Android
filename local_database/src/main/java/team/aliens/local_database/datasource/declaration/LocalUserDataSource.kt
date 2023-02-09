package team.aliens.local_database.datasource.declaration

import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.UserPersonalKeyParam
import team.aliens.local_database.param.user.UserInfoParam

interface LocalUserDataSource {

    suspend fun setPersonalKey(personalKeyParam: UserPersonalKeyParam)
    suspend fun fetchPersonalKey(): UserPersonalKeyParam

    suspend fun setUserVisibleInform(featuresParam: FeaturesParam)
    suspend fun fetchUserVisibleInform(): FeaturesParam

    suspend fun setUserInfo(userInfoParam: UserInfoParam)
    suspend fun fetchUserInfo(): UserInfoParam
}
