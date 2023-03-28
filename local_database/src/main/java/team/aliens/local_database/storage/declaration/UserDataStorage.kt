package team.aliens.local_database.storage.declaration

import team.aliens.local_database.param.UserVisibleParam
import team.aliens.local_database.param.UserPersonalKeyParam

interface UserDataStorage {

    fun setPersonalKey(personalKeyParam: UserPersonalKeyParam)

    fun fetchAccessToken(): String
    fun fetchAccessTokenExpiredAt(): String
    fun fetchRefreshToken(): String
    fun fetchRefreshTokenExpiredAt(): String

    fun clearToken()

    fun setUserVisible(userVisibleParam: UserVisibleParam)

    fun fetchMealServiceBoolean(): Boolean
    fun fetchNoticeServiceBoolean(): Boolean
    fun fetchPointServiceBoolean(): Boolean
    fun fetchStudyRoomServiceBoolean(): Boolean
    fun fetchRemainServiceBoolean(): Boolean

    fun setAutoSignInOption(autoSignInEnabled: Boolean)
    fun fetchAutoSignInOption(): Boolean

    fun signOut()
}
