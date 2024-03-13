package team.aliens.dms.android.core.jwt.datastore

import team.aliens.dms.android.core.jwt.Tokens

abstract class JwtDataStoreDataSource {

    abstract fun loadTokens(): Tokens

    abstract suspend fun storeTokens(tokens: Tokens)

    abstract suspend fun clearTokens()
}
