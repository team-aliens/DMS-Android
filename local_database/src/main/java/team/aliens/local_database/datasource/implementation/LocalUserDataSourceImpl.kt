package team.aliens.local_database.datasource.implementation

import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.localutil.toLocalDateTime
import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.UserPersonalKeyParam
import team.aliens.local_database.storage.declaration.UserDataStorage
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val userDataStorage: UserDataStorage,
) : LocalUserDataSource {

    override suspend fun setPersonalKey(personalKeyParam: UserPersonalKeyParam) {
        userDataStorage.setPersonalKey(personalKeyParam)
    }

    override suspend fun fetchPersonalKey(): UserPersonalKeyParam {
        userDataStorage.apply {
            return UserPersonalKeyParam(
                fetchAccessToken(),
                fetchAccessTokenExpiredAt().toLocalDateTime(),
                fetchRefreshToken(),
                fetchRefreshTokenExpiredAt().toLocalDateTime(),
            )
        }
    }

    override suspend fun setUserVisibleInform(featuresParam: FeaturesParam) {
        userDataStorage.setUserVisible(featuresParam)
    }

    override suspend fun fetchUserVisibleInform(): FeaturesParam {
        userDataStorage.apply {
            return FeaturesParam(
                fetchMealServiceBoolean(),
                fetchNoticeServiceBoolean(),
                fetchPointServiceBoolean(),
            )
        }
    }

    override suspend fun setAutoSignInOption(autoSignInEnabled: Boolean) {
        userDataStorage.setAutoSignInOption(autoSignInEnabled)
    }

    override suspend fun fetchAutoSignInOption(): Boolean {
        return userDataStorage.fetchAutoSignInOption()
    }
}
