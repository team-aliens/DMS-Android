package team.aliens.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import team.aliens.data._datasource.local.LocalAuthDataSource
import team.aliens.domain._model.auth.Token
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalAuthDataSource {

    override suspend fun findToken(): Token {
        TODO("Not yet implemented")
    }

    override suspend fun findAccessToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        TODO("Not yet implemented")
    }

    override suspend fun findRefreshToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun findRefreshTokenExpiredAt(): String {
        TODO("Not yet implemented")
    }

    override suspend fun saveToken(
        token: Token,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun clearToken() {
        TODO("Not yet implemented")
    }

    override suspend fun findAutoSignInOption(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    ) {
        TODO("Not yet implemented")
    }
}
