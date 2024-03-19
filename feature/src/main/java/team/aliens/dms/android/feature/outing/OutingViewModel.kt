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
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import team.aliens.dms.android.data.outing.repository.OutingRepository
import team.aliens.dms.android.shared.date.util.now
import javax.inject.Inject

@HiltViewModel
class OutingViewModel @Inject constructor(
    private val outingRepository: OutingRepository,
) : BaseMviViewModel<OutingUiState, OutingIntent, OutingSideEffect>(
    initialState = OutingUiState.initial(),
) {
    init {
        fetchOutingApplicationTime()
        fetchCurrentAppliedOutingApplication()
        fetchOutingTypes()
    }

    override fun processIntent(intent: OutingIntent) {
        when (intent) {
            is OutingIntent.CancelCurrentApplication -> cancelApplication()
            is OutingIntent.UpdateSelectedOutingType -> updateSelectedOutingType(value = intent.value)
        }
    }

    private fun fetchOutingApplicationTime() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchOutingApplicationTimes(dayOfWeek = now.dayOfWeek)
        }.onSuccess {
            val outingApplicationTime = it.first()
            reduce(
                newState = stateFlow.value.copy(
                    outingApplicationTime = outingApplicationTime,
                ),
            )
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

    private fun fetchOutingTypes() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchOutingTypes("")
        }.onSuccess { fetchedOutingTypes ->
            reduce(
                newState = stateFlow.value.copy(
                    outingTypes = fetchedOutingTypes,
                ),
            )
        }
    }

    private fun updateSelectedOutingType(value: String) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingType = value,
        ),
    )
}

data class OutingUiState(
    val outingApplicationTime: OutingApplicationTime?,
    val currentAppliedOutingApplication: CurrentAppliedOutingApplication?,
    val outingTypes: List<String>?,
    val selectedOutingType: String?,
) : UiState() {
    companion object {
        fun initial() = OutingUiState(
            outingApplicationTime = null,
            currentAppliedOutingApplication = null,
            outingTypes = null,
            selectedOutingType = null,
        )
    }
}

sealed class OutingIntent : Intent() {
    data object CancelCurrentApplication : OutingIntent()
    class UpdateSelectedOutingType(val value: String) : OutingIntent()
}

sealed class OutingSideEffect : SideEffect() {
    data object CurrentAppliedOutingApplicationNotFound : OutingSideEffect()
}
