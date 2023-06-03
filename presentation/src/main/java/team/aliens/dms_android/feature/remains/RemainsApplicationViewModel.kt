package team.aliens.dms_android.feature.remains

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.remains.CurrentAppliedRemainsOption
import team.aliens.domain.model.remains.RemainsApplicationTime
import team.aliens.domain.model.remains.RemainsOption
import team.aliens.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.domain.usecase.remain.FetchRemainsApplicationTimeUseCase
import team.aliens.domain.usecase.remain.FetchRemainsOptionsUseCase
import team.aliens.domain.usecase.remain.UpdateRemainsOptionUseCase
import javax.inject.Inject

@HiltViewModel
internal class RemainsApplicationViewModel @Inject constructor(
    private val fetchRemainsApplicationTimeUseCase: FetchRemainsApplicationTimeUseCase,
    private val fetchRemainsOptionsUseCase: FetchRemainsOptionsUseCase,
    private val fetchCurrentAppliedRemainsOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
    private val updateRemainsOptionUseCase: UpdateRemainsOptionUseCase,
) : MviViewModel<RemainsApplicationUiState, RemainsApplicationUiEvent>(
    initialState = RemainsApplicationUiState.initial(),
) {
    init {
        fetchRemainsApplicationTime()
        fetchCurrentAppliedRemainsOption()
        fetchRemainsOptions()
    }

    private val currentAppliedRemainsOption: CurrentAppliedRemainsOption?
        get() = uiState.value.currentAppliedRemainsOption
    private val selectedRemainsOption: RemainsOption?
        get() = uiState.value.selectedRemainsOption

    override fun updateState(event: RemainsApplicationUiEvent) {
        when (event) {
            RemainsApplicationUiEvent.FetchRemainsApplicationTime -> fetchRemainsApplicationTime()
            RemainsApplicationUiEvent.FetchRemainsOptions -> fetchRemainsOptions()
            RemainsApplicationUiEvent.FetchCurrentAppliedRemainsOption -> fetchCurrentAppliedRemainsOption()
            is RemainsApplicationUiEvent.SelectRemainsOption -> setSelectedRemainsOption(event.remainsOption)
            RemainsApplicationUiEvent.UpdateRemainsOption -> updateRemainsOption()
        }
    }

    private fun fetchRemainsApplicationTime() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchRemainsApplicationTimeUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        remainsApplicationTime = it,
                    ),
                )
            }
        }
    }

    private fun fetchRemainsOptions() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchRemainsOptionsUseCase()
            }.onSuccess { fetchedRemainsOptions ->
                val appliedRemainsOption = fetchedRemainsOptions.first { it.applied }

                setState(
                    newState = uiState.value.copy(
                        remainsOptions = fetchedRemainsOptions,
                        selectedRemainsOption = appliedRemainsOption,
                        applicationButtonEnabled = appliedRemainsOption.id != selectedRemainsOption?.id,
                    ),
                )
            }
        }
    }

    private fun fetchCurrentAppliedRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchCurrentAppliedRemainsOptionUseCase()
            }.onSuccess { fetchedCurrentAppliedRemainsOption ->
                setState(
                    newState = uiState.value.copy(
                        currentAppliedRemainsOption = fetchedCurrentAppliedRemainsOption,
                    ),
                )
            }
        }
    }

    private fun updateRemainsOption() {
        setRemainsApplicationButtonState(false)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                updateRemainsOptionUseCase(
                    updateRemainsOptionInput = UpdateRemainsOptionInput(
                        remainsOptionId = uiState.value.selectedRemainsOption?.id
                            ?: throw IllegalStateException(),
                    ),
                )
            }.onSuccess {
                fetchCurrentAppliedRemainsOption()
                fetchRemainsOptions()
            }
        }
    }

    private fun setRemainsApplicationButtonState(
        enabled: Boolean,
    ) {
        setState(
            newState = uiState.value.copy(
                applicationButtonEnabled = enabled,
            ),
        )
    }

    private fun setSelectedRemainsOption(remainsOption: RemainsOption) {
        setState(
            newState = uiState.value.copy(
                selectedRemainsOption = remainsOption,
                applicationButtonEnabled = remainsOption.id != currentAppliedRemainsOption?.appliedRemainsOptionId,
            ),
        )
    }
}

internal data class RemainsApplicationUiState(
    val selectedRemainsOption: RemainsOption?,
    val currentAppliedRemainsOption: CurrentAppliedRemainsOption?,
    val remainsApplicationTime: RemainsApplicationTime?,
    val remainsOptions: List<RemainsOption>,
    val applicationButtonEnabled: Boolean,
) : UiState {
    companion object {
        fun initial() = RemainsApplicationUiState(
            selectedRemainsOption = null,
            remainsApplicationTime = null,
            currentAppliedRemainsOption = null,
            remainsOptions = emptyList(),
            applicationButtonEnabled = false,
        )
    }
}


internal sealed class RemainsApplicationUiEvent : UiEvent {
    object FetchRemainsApplicationTime : RemainsApplicationUiEvent()
    object FetchRemainsOptions : RemainsApplicationUiEvent()
    object FetchCurrentAppliedRemainsOption : RemainsApplicationUiEvent()
    object UpdateRemainsOption : RemainsApplicationUiEvent()
    class SelectRemainsOption(val remainsOption: RemainsOption) : RemainsApplicationUiEvent()
}
