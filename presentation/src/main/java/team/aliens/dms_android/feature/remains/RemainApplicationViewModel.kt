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
import team.aliens.domain.model.remains.toModel
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
        fetchRemainsOptions()
        fetchCurrentAppliedRemainsOption()
    }

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
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        remainsOptions = it.remainOptions.toModel(),
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
        val selectedRemainsOption =
            uiState.value.selectedRemainsOption ?: TODO("잔류 항목을 선택해주세요 에러 핸들링")

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                updateRemainsOptionUseCase(
                    updateRemainsOptionInput = UpdateRemainsOptionInput(
                        remainsOptionId = selectedRemainsOption.id,
                    ),
                )
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        selectedRemainsOption = selectedRemainsOption,
                    ),
                )
            }
        }
    }

    private fun setSelectedRemainsOption(remainsOption: RemainsOption) {
        val currentAppliedRemainsOptionId =
            uiState.value.currentAppliedRemainsOption?.appliedRemainsOptionId

        setState(
            newState = uiState.value.copy(
                selectedRemainsOption = remainsOption,
                applicationButtonEnabled = remainsOption.id != currentAppliedRemainsOptionId,
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
