package team.aliens.dms.android.feature.onboarding.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.onboarding.datastore.OnboardingDataStoreDataSource
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingDataSource: OnboardingDataStoreDataSource,
) : BaseStateViewModel<OnboardingState, Unit>(OnboardingState.initialState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                onboardingDataSource.getOnboardingCompleted()
            }.onSuccess { isCompleted ->
                setState {
                    stateFlow.value.copy(
                        isOnboardingCompleted = isCompleted,
                    )
                }
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingDataSource.setOnboardingCompleted(true)
            setState { stateFlow.value.copy(isOnboardingCompleted = true) }
        }
    }
}

data class OnboardingState(
    val isOnboardingCompleted: Boolean,
) : UiState() {
    companion object {
        fun initialState() = OnboardingState(
            isOnboardingCompleted = false,
        )
    }
}
