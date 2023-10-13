package team.aliens.dms_android.data.datasource.local

import team.aliens.dms.android.domain.model.auth.Token

interface LocalAuthDataSource {

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

    suspend fun findAutoSignInOption(): Boolean

    suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    )
}
