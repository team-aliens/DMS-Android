package team.aliens.dms.android.feature.outing

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.repository.OutingRepository
import javax.inject.Inject

@HiltViewModel
class OutingViewModel @Inject constructor(
    private val outingRepository: OutingRepository,
) : BaseMviViewModel<OutingUiState, OutingIntent, OutingSideEffect>(
    initialState = OutingUiState.initial(),
) {
    init {
        fetchCurrentAppliedOutingApplication()
    }

    override fun processIntent(intent: OutingIntent) {
        when (intent) {
            is OutingIntent.CancelCurrentApplication -> cancelApplication()
        }
    }

    private fun fetchCurrentAppliedOutingApplication() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchCurrentAppliedOutingApplication()
        }.onSuccess { fetchedOutingApplication ->
            reduce(
                newState = stateFlow.value.copy(
                    currentAppliedOutingApplication = fetchedOutingApplication,
                ),
            )
        }.onFailure {
            postSideEffect(OutingSideEffect.CurrentAppliedOutingApplicationNotFound)
        }
    }

    private fun cancelApplication() = viewModelScope.launch(Dispatchers.IO) {

    }
}

data class OutingUiState(
    // val outingApplicationTime: List<OutingApplicationTime>?,
    val currentAppliedOutingApplication: CurrentAppliedOutingApplication?,
) : UiState() {
    companion object {
        fun initial() = OutingUiState(
            currentAppliedOutingApplication = null,
        )
    }
}

sealed class OutingIntent : Intent() {
    data object CancelCurrentApplication : OutingIntent()
}

sealed class OutingSideEffect : SideEffect() {
    data object CurrentAppliedOutingApplicationNotFound : OutingSideEffect()
}
