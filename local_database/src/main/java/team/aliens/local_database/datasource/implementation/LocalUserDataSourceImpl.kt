package team.aliens.local_database.datasource.implementation

import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.localutil.toLocalDateTime
import team.aliens.local_database.param.UserVisibleParam
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

    override suspend fun setUserVisibleInform(userVisibleParam: UserVisibleParam) {
        userDataStorage.setUserVisible(userVisibleParam)
    }

    override suspend fun fetchUserVisibleInform(): UserVisibleParam {
        userDataStorage.apply {
            return UserVisibleParam(
                fetchMealServiceBoolean(),
                fetchNoticeServiceBoolean(),
                fetchPointServiceBoolean(),
                fetchStudyRoomServiceBoolean(),
                fetchRemainServiceBoolean(),
            )
        }
    }

    override suspend fun setAutoSignInOption(autoSignInEnabled: Boolean) {
        userDataStorage.setAutoSignInOption(autoSignInEnabled)
    }

    override suspend fun fetchAutoSignInOption(): Boolean {
        return userDataStorage.fetchAutoSignInOption()
    }

    override suspend fun signOut() {
        userDataStorage.signOut()
    }
}
