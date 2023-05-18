package team.aliens.data.datasource.local

import team.aliens.domain._model.auth.Token

interface LocalAuthDataSource {

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
