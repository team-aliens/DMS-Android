package team.aliens.local_database.storage.declaration

import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.UserPersonalKeyParam

interface UserDataStorage {

    fun setPersonalKey(personalKeyParam: UserPersonalKeyParam)

    fun fetchAccessToken(): String
    fun fetchAccessTokenExpiredAt(): String
    fun fetchRefreshToken(): String
    fun fetchRefreshTokenExpiredAt(): String

    fun clearToken()

    fun setUserVisible(featuresParam: FeaturesParam)

    fun fetchMealServiceBoolean(): Boolean
    fun fetchNoticeServiceBoolean(): Boolean
    fun fetchPointServiceBoolean(): Boolean

    fun setAutoSignInOption(autoSignInEnabled: Boolean)
    fun fetchAutoSignInOption(): Boolean
}
