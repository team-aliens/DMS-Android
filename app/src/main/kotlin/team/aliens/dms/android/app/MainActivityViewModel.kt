package team.aliens.dms.android.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    jwtProvider: JwtProvider,
    private val onboardingDataSource: OnboardingDataStoreDataSource,
) : ViewModel() {
    val autoSignInAvailable: StateFlow<Boolean> = jwtProvider.isCachedAccessTokenAvailable

    private val _isUpdateFailed = MutableStateFlow(false)
    val isUpdateFailed = _isUpdateFailed.asStateFlow()

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

    init {
        viewModelScope.launch {
            _isOnboardingCompleted.value = onboardingDataSource.getOnboardingCompleted()
        }
    }

    fun onUpdateFailed() {
        _isUpdateFailed.value = true
    }
}
