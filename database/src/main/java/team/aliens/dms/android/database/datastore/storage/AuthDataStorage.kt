package team.aliens.dms.android.database.datastore.storage

import team.aliens.dms.android.domain.model.auth.Token

// TODO: remove
@Deprecated("No usage")
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
