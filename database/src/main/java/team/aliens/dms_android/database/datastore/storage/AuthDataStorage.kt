package team.aliens.dms_android.database.datastore.storage

import team.aliens.dms_android.domain.model.auth.Token

interface AuthDataStorage {

    suspend fun findToken(): Token

    suspend fun findAccessToken(): String

    suspend fun checkAccessTokenAvailable(): Boolean

    suspend fun findAccessTokenExpiredAt(): String

    suspend fun findRefreshToken(): String

    suspend fun checkRefreshTokenAvailable(): Boolean

    suspend fun findRefreshTokenExpiredAt(): String

    suspend fun saveToken(
        token: Token,
    )

    suspend fun clearToken()

    suspend fun signOut()

    suspend fun findAutoSignInOption(): Boolean

    suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    )
}
