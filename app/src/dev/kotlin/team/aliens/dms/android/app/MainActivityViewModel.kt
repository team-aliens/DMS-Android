package team.aliens.dms.android.app

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.ui.viewmodel.BaseViewModel
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val jwtProvider: JwtProvider,
    private val onboardingDataSource: OnboardingDataStoreDataSource,
) : BaseViewModel() {
    val autoSignInAvailable: StateFlow<Boolean> = jwtProvider.isCachedAccessTokenAvailable

    private val _isUpdateFailed = MutableStateFlow(false)
    val isUpdateFailed = _isUpdateFailed.asStateFlow()

    private val _isOnboardingCompleted = MutableStateFlow<Boolean?>(null)
    val isOnboardingCompleted: StateFlow<Boolean?> = _isOnboardingCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            _isOnboardingCompleted.value = onboardingDataSource.getOnboardingCompleted()
        }
    }

    fun onUpdateFailed() {
        _isUpdateFailed.value = true
    }

    fun consumeUpdateFailed() {
        _isUpdateFailed.value = false
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingDataSource.setOnboardingCompleted(true)
            _isOnboardingCompleted.value = true
        }
    }
}
