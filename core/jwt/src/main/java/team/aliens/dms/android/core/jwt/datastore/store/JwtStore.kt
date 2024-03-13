package team.aliens.dms.android.core.jwt.datastore.store

import team.aliens.dms.android.core.jwt.Tokens

internal abstract class JwtStore {

    abstract fun loadTokens(): Tokens

    abstract suspend fun storeTokens(tokens: Tokens)

    abstract suspend fun clearTokens()
}
