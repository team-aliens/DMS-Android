package team.aliens.dms.android.core.jwt

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.android.core.jwt.exception.CannotUseAccessTokenException
import team.aliens.dms.android.core.jwt.exception.CannotUseRefreshTokenException
import team.aliens.dms.android.core.jwt.network.JwtReissueManager
import team.aliens.dms.android.core.jwt.network.exception.CannotReissueTokenException
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import javax.inject.Inject

internal class JwtProviderImpl @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
    private val jwtReissueManager: JwtReissueManager,
    private val sessionCleaner: SessionCleaner,
) : JwtProvider() {
    private val tokenMutex = Mutex()

    private var _cachedAccessToken: AccessToken? = null

    override val cachedAccessToken: AccessToken
        get() {
            val accessToken =
                _cachedAccessToken ?: throw CannotUseAccessTokenException()

            if (accessToken.isExpired()) {
                throw CannotUseAccessTokenException()
            }

            return accessToken
        }

    private val _isCachedAccessTokenAvailable =
        MutableStateFlow(checkIsAccessTokenAvailable())

    override val isCachedAccessTokenAvailable: StateFlow<Boolean> =
        _isCachedAccessTokenAvailable.asStateFlow()

    private var _cachedRefreshToken: RefreshToken? = null

    override val cachedRefreshToken: RefreshToken
        get() {
            val refreshToken =
                _cachedRefreshToken ?: throw CannotUseRefreshTokenException()

            if (refreshToken.isExpired()) {
                throw CannotUseRefreshTokenException()
            }

            return refreshToken
        }

    private val _isCachedRefreshTokenAvailable =
        MutableStateFlow(checkIsRefreshTokenAvailable())

    override val isCachedRefreshTokenAvailable: StateFlow<Boolean> =
        _isCachedRefreshTokenAvailable.asStateFlow()

    init {
        loadTokens()
    }

    private fun loadTokens() {
        runCatching {
            jwtDataStoreDataSource.loadTokens()
        }.onSuccess { tokens ->
            _cachedAccessToken = tokens.accessToken
            _cachedRefreshToken = tokens.refreshToken
        }.onFailure { exception ->
            Log.e("JwtProvider", "Failed to load tokens", exception)
        }

        refreshTokenAbility()
    }

    override fun updateTokens(tokens: Tokens) {
        runBlocking {
            tokenMutex.withLock {
                updateTokensLocked(tokens = tokens)
            }
        }
    }

    override fun clearCaches() {
        runBlocking {
            tokenMutex.withLock {
                clearCachesLocked()
            }
        }
    }

    override suspend fun resolveSession(): Boolean {
        val reissueResult = tokenMutex.withLock {
            val accessToken = _cachedAccessToken

            if (accessToken != null && !accessToken.isExpired()) {
                refreshTokenAbility()
                return@withLock ReissueResult(
                    isReissued = true,
                    isSessionEnded = false,
                )
            }

            val result = reissueTokensLocked()
            refreshTokenAbility()

            result
        }

        if (reissueResult.isSessionEnded) {
            sessionCleaner.clearSession(tokensAlreadyCleared = true)
        }

        return reissueResult.isReissued && checkIsAccessTokenAvailable()
    }

    override suspend fun refreshSession(): Boolean {
        val reissueResult = tokenMutex.withLock {
            val result = reissueTokensLocked()
            refreshTokenAbility()

            result
        }

        if (reissueResult.isSessionEnded) {
            sessionCleaner.clearSession(tokensAlreadyCleared = true)
        }

        return reissueResult.isReissued && checkIsAccessTokenAvailable()
    }

    private fun refreshTokenAbility() {
        _isCachedAccessTokenAvailable.value =
            checkIsAccessTokenAvailable()
        _isCachedRefreshTokenAvailable.value =
            checkIsRefreshTokenAvailable()
    }

    private fun checkIsAccessTokenAvailable(): Boolean =
        _cachedAccessToken?.let { accessToken ->
            !accessToken.isExpired()
        } ?: false

    private fun checkIsRefreshTokenAvailable(): Boolean =
        _cachedRefreshToken?.let { refreshToken ->
            !refreshToken.isExpired()
        } ?: false

    private suspend fun reissueTokensLocked(): ReissueResult {
        val refreshToken = _cachedRefreshToken

        if (refreshToken == null || refreshToken.isExpired()) {
            clearCachesLocked()
            return ReissueResult(
                isReissued = false,
                isSessionEnded = true,
            )
        }

        return try {
            val tokens = jwtReissueManager(
                refreshToken = refreshToken.value,
            )

            updateTokensLocked(tokens = tokens)
            ReissueResult(
                isReissued = true,
                isSessionEnded = false,
            )
        } catch (exception: CannotReissueTokenException) {
            val isSessionEnded =
                exception.statusCode == 401 ||
                    exception.statusCode == 404

            if (isSessionEnded) {
                clearCachesLocked()
            }

            ReissueResult(
                isReissued = false,
                isSessionEnded = isSessionEnded,
            )
        }
    }

    private suspend fun updateTokensLocked(tokens: Tokens) {
        val previousAccessToken = _cachedAccessToken
        val previousRefreshToken = _cachedRefreshToken

        _cachedAccessToken = tokens.accessToken
        _cachedRefreshToken = tokens.refreshToken
        refreshTokenAbility()

        runCatchingCancellable {
            jwtDataStoreDataSource.storeTokens(tokens = tokens)
        }.onFailure { exception ->
            _cachedAccessToken = previousAccessToken
            _cachedRefreshToken = previousRefreshToken
            refreshTokenAbility()

            Log.e(
                "JwtProvider",
                "Failed to store tokens",
                exception,
            )

            throw IllegalStateException(
                "Failed to persist tokens",
                exception,
            )
        }
    }

    private suspend fun clearCachesLocked() {
        val previousAccessToken = _cachedAccessToken
        val previousRefreshToken = _cachedRefreshToken

        _cachedAccessToken = null
        _cachedRefreshToken = null
        refreshTokenAbility()

        runCatchingCancellable {
            jwtDataStoreDataSource.clearTokens()
        }.onFailure { exception ->
            _cachedAccessToken = previousAccessToken
            _cachedRefreshToken = previousRefreshToken
            refreshTokenAbility()

            Log.e(
                "JwtProvider",
                "Failed to clear tokens",
                exception,
            )

            throw IllegalStateException(
                "Failed to clear persisted tokens",
                exception,
            )
        }
    }
}

private data class ReissueResult(
    val isReissued: Boolean,
    val isSessionEnded: Boolean,
)
