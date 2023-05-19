package team.aliens.local.datastore.storage

import team.aliens.domain.model.auth.Token

interface AuthDataStorage {

    suspend fun findToken(): Token

    suspend fun findAccessToken(): String

    suspend fun findAccessTokenExpiredAt(): String

    suspend fun findRefreshToken(): String

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
