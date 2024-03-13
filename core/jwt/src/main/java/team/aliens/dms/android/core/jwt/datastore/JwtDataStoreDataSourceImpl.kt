package team.aliens.dms.android.core.jwt.datastore

import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.jwt.datastore.store.JwtStore
import javax.inject.Inject

internal class JwtDataStoreDataSourceImpl @Inject constructor(
    private val jwtStore: JwtStore,
) : JwtDataStoreDataSource() {

    override fun loadTokens(): Tokens = jwtStore.loadTokens()

    override suspend fun storeTokens(tokens: Tokens) {
        jwtStore.storeTokens(tokens)
    }

    override suspend fun clearTokens() {
        jwtStore.clearTokens()
    }
}
