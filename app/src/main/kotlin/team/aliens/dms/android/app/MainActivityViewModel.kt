package team.aliens.dms.android.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.theme.ThemeMode
import team.aliens.dms.android.core.theme.datastore.ThemeDataStoreDataSource
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSource
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val jwtProvider: JwtProvider,
    private val onboardingDataSource: OnboardingDataStoreDataSource,
    private val themeDataStoreDataSource: ThemeDataStoreDataSource,
) : ViewModel() {
    val autoSignInAvailable: StateFlow<Boolean> = jwtProvider.isCachedRefreshTokenAvailable

    private val _isUpdateFailed = MutableStateFlow(false)
    val isUpdateFailed = _isUpdateFailed.asStateFlow()

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

    private val _isStartupResolved = MutableStateFlow(false)
    val isStartupResolved: StateFlow<Boolean> = _isStartupResolved.asStateFlow()

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        viewModelScope.launch {
            runCatchingCancellable { jwtProvider.resolveSession() }
            _isOnboardingCompleted.value = onboardingDataSource.getOnboardingCompleted()
            _isStartupResolved.value = true
        }
        viewModelScope.launch {
            themeDataStoreDataSource.getThemeModeFlow().collect { mode ->
                _themeMode.value = mode
            }
        }
    }

    fun resolveSession() {
        viewModelScope.launch {
            runCatchingCancellable { jwtProvider.resolveSession() }
        }
    }

    fun onUpdateFailed() {
        _isUpdateFailed.value = true
    }
}
