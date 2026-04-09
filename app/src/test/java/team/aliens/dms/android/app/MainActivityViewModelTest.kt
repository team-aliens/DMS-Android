package team.aliens.dms.android.app

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSource
import team.aliens.dms.android.shared.date.util.now

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun init_resolvesSessionAndMarksStartupResolved() = runTest(mainDispatcherRule.dispatcher) {
        val jwtProvider = FakeJwtProvider(isJwtAvailable = true)
        val onboardingDataSource = FakeOnboardingDataStoreDataSource(isCompleted = true)

        val viewModel = MainActivityViewModel(
            jwtProvider = jwtProvider,
            onboardingDataSource = onboardingDataSource,
        )

        advanceUntilIdle()

        assertEquals(1, jwtProvider.resolveSessionCallCount)
        assertTrue(viewModel.isStartupResolved.value)
        assertTrue(viewModel.isOnboardingCompleted.value)
        assertTrue(viewModel.autoSignInAvailable.value)
    }

    @Test
    fun resolveSession_rechecksSessionAvailability() = runTest(mainDispatcherRule.dispatcher) {
        val jwtProvider = FakeJwtProvider(isJwtAvailable = true)
        val onboardingDataSource = FakeOnboardingDataStoreDataSource(isCompleted = true)

        val viewModel = MainActivityViewModel(
            jwtProvider = jwtProvider,
            onboardingDataSource = onboardingDataSource,
        )
        advanceUntilIdle()

        jwtProvider.updateRefreshAvailability(false)

        viewModel.resolveSession()
        advanceUntilIdle()

        assertEquals(2, jwtProvider.resolveSessionCallCount)
        assertFalse(viewModel.autoSignInAvailable.value)
    }
}

private class FakeJwtProvider(
    isJwtAvailable: Boolean,
) : JwtProvider() {
    private val accessToken = AccessToken(value = "access-token", expiration = now.plusDays(1))
    private val refreshToken = RefreshToken(value = "refresh-token", expiration = now.plusDays(1))

    private val _isCachedAccessTokenAvailable = MutableStateFlow(isJwtAvailable)
    override val isCachedAccessTokenAvailable: StateFlow<Boolean> = _isCachedAccessTokenAvailable

    private val _isCachedRefreshTokenAvailable = MutableStateFlow(isJwtAvailable)
    override val isCachedRefreshTokenAvailable: StateFlow<Boolean> = _isCachedRefreshTokenAvailable

    override val cachedAccessToken: AccessToken
        get() = accessToken

    override val cachedRefreshToken: RefreshToken
        get() = refreshToken

    var resolveSessionCallCount: Int = 0
        private set

    override fun updateTokens(tokens: Tokens) = Unit

    override fun clearCaches() = Unit

    override suspend fun resolveSession(): Boolean {
        resolveSessionCallCount++
        return isCachedRefreshTokenAvailable.value
    }

    fun updateRefreshAvailability(isAvailable: Boolean) {
        _isCachedAccessTokenAvailable.value = isAvailable
        _isCachedRefreshTokenAvailable.value = isAvailable
    }
}

private class FakeOnboardingDataStoreDataSource(
    private val isCompleted: Boolean,
) : OnboardingDataStoreDataSource() {
    override suspend fun setOnboardingCompleted(isCompleted: Boolean) = Unit

    override suspend fun getOnboardingCompleted(): Boolean = isCompleted
}
