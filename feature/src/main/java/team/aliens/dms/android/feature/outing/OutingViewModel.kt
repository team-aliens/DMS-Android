package team.aliens.dms.android.feature.outing

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import team.aliens.dms.android.data.outing.repository.OutingRepository
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.date.util.today
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
            is OutingIntent.UpdateReason -> updateReason(value = intent.value)
            is OutingIntent.UpdateOutingStartTime -> updateOutingStartTime(value = intent.value)
            is OutingIntent.UpdateOutingEndTime -> updateOutingEndTime(value = intent.value)
        }
    }

    private fun fetchOutingApplicationTime() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchOutingApplicationTimes(dayOfWeek = today.dayOfWeek)
        }.onSuccess { fetchedApplicationTime ->
            reduce(
                newState = stateFlow.value.copy(
                    outingApplicationTime = fetchedApplicationTime.first(),
                ),
            )
        }.onFailure {
            it.printStackTrace()
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
            outingRepository.fetchOutingTypes(null)
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

    private fun updateReason(value: String) = reduce(
        newState = stateFlow.value.copy(
            reason = value,
        ),
    )

    private fun updateOutingStartTime(value: String) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingStartTime = value,
        ),
    )


    private fun updateOutingEndTime(value: String) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingEndTime = value,
        ),
    )
}

data class OutingUiState(
    val outingApplicationTime: OutingApplicationTime?,
    val currentAppliedOutingApplication: CurrentAppliedOutingApplication?,
    val outingTypes: List<String>?,
    val selectedOutingType: String?,
    val reason: String,
    val capturedNow: LocalDateTime,
    val selectedOutingStartTime: String,
    val selectedOutingEndTime: String,
) : UiState() {
    companion object {
        fun initial(): OutingUiState {
            val capturedNow = now
            return OutingUiState(
                outingApplicationTime = null,
                currentAppliedOutingApplication = null,
                outingTypes = null,
                selectedOutingType = null,
                reason = "",
                capturedNow = capturedNow,
                // TODO: remove hard-coded string resources from viewmodel
                selectedOutingEndTime = "${capturedNow.hour}:${capturedNow.minute}",
                selectedOutingStartTime = "${capturedNow.hour}:${capturedNow.minute}",
            )
        }
    }
}

sealed class OutingIntent : Intent() {
    data object CancelCurrentApplication : OutingIntent()
    class UpdateSelectedOutingType(val value: String) : OutingIntent()
    class UpdateReason(val value: String) : OutingIntent()
    class UpdateOutingStartTime(val value: String) : OutingIntent()
    class UpdateOutingEndTime(val value: String) : OutingIntent()
}

sealed class OutingSideEffect : SideEffect() {
    data object CurrentAppliedOutingApplicationNotFound : OutingSideEffect()
}