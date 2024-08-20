package team.aliens.dms.android.feature.remains

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.model.RemainsApplicationTime
import team.aliens.dms.android.data.remains.model.RemainsOption
import team.aliens.dms.android.data.remains.repository.RemainsRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class RemainsApplicationViewModel @Inject constructor(
    private val remainsRepository: RemainsRepository,
) : BaseMviViewModel<RemainsApplicationUiState, RemainsApplicationIntent, RemainsApplicationSideEffect>(
    initialState = RemainsApplicationUiState.initial(),
) {
    init {
        fetchRemainsApplicationTime()
        fetchRemainsOptions()
        fetchAppliedRemainsOption()
    }

    override fun processIntent(intent: RemainsApplicationIntent) {
        when (intent) {
            is RemainsApplicationIntent.UpdateSelectedRemainsOption ->
                updateSelectedRemainsOption(intent.optionId)

            RemainsApplicationIntent.UpdateRemainsOption -> updateRemainsOption()
        }
    }

    private fun fetchRemainsApplicationTime() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                remainsRepository.fetchRemainsApplicationTime()
            }.onSuccess { fetchedRemainsApplicationTime ->
                reduce(
                    newState = stateFlow.value.copy(
                        remainsApplicationTime = fetchedRemainsApplicationTime,
                    ),
                )
            }
        }
    }

    private fun fetchRemainsOptions() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                remainsRepository.fetchRemainsOptions()
            }.onSuccess { fetchedRemainsOptions ->
                reduce(
                    newState = stateFlow.value.copy(
                        remainsOptions = fetchedRemainsOptions,
                        selectedRemainsOptionId = fetchedRemainsOptions.find { it.applied }?.id,
                    ),
                )
            }
        }
    }

    private fun fetchAppliedRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                remainsRepository.fetchAppliedRemainsOption()
            }.onSuccess { fetchedAppliedRemainsOption ->
                reduce(
                    newState = stateFlow.value.copy(
                        appliedRemainsOption = fetchedAppliedRemainsOption,
                    ),
                )
                this@RemainsApplicationViewModel.updateSelectedRemainsOption(
                    fetchedAppliedRemainsOption.id,
                )
            }
        }
    }

    private fun updateSelectedRemainsOption(optionId: UUID): Boolean = reduce(
        newState = stateFlow.value.copy(
            selectedRemainsOptionId = optionId,
            applicationButtonEnabled = optionId != stateFlow.value.appliedRemainsOption?.id,
        ),
    )

    private fun updateRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                remainsRepository.updateRemainsOption(
                    optionId = stateFlow.value.selectedRemainsOptionId!!,
                )
            }.onSuccess {
                this@RemainsApplicationViewModel.fetchRemainsOptions()
                this@RemainsApplicationViewModel.fetchAppliedRemainsOption()
                postSideEffect(RemainsApplicationSideEffect.UpdateSuccess)
            }
        }
    }
}

internal data class RemainsApplicationUiState(
    val selectedRemainsOptionId: UUID?,
    val remainsApplicationTime: RemainsApplicationTime?,
    val appliedRemainsOption: AppliedRemainsOption?,
    val applicationButtonEnabled: Boolean,
    val remainsOptions: List<RemainsOption>,
) : UiState() {
    companion object {
        fun initial() = RemainsApplicationUiState(
            selectedRemainsOptionId = null,
            remainsApplicationTime = null,
            appliedRemainsOption = null,
            applicationButtonEnabled = false,
            remainsOptions = emptyList(),
        )
    }
}

internal sealed class RemainsApplicationIntent : Intent() {
    class UpdateSelectedRemainsOption(val optionId: UUID) : RemainsApplicationIntent()
    data object UpdateRemainsOption : RemainsApplicationIntent()
}

internal sealed class RemainsApplicationSideEffect : SideEffect() {
    data object UpdateSuccess : RemainsApplicationSideEffect()
}

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.MviViewModel
import team.aliens.dms.android.feature._legacy.base.UiEvent
import team.aliens.dms.android.feature._legacy.base.UiState
import team.aliens.dms.android.domain.model.remains.CurrentAppliedRemainsOption
import team.aliens.dms.android.domain.model.remains.RemainsApplicationTime
import team.aliens.dms.android.domain.model.remains.RemainsOption
import team.aliens.dms.android.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.dms.android.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.dms.android.domain.usecase.remain.FetchRemainsApplicationTimeUseCase
import team.aliens.dms.android.domain.usecase.remain.FetchRemainsOptionsUseCase
import team.aliens.dms.android.domain.usecase.remain.UpdateRemainsOptionUseCase
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
                if (fetchedRemainsOptions.isNotEmpty()) {
                    val appliedRemainsOption = fetchedRemainsOptions.firstOrNull { it.applied }

                    setState(
                        newState = uiState.value.copy(
                            remainsOptions = fetchedRemainsOptions,
                            selectedRemainsOption = appliedRemainsOption,
                        ),
                    )
                }
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
            }.onFailure {
                setRemainsApplicationButtonState(true)
            }
        }
    }

    private fun setRemainsApplicationButtonState(
        // todo side effect로 기능 재구현 필요!! false 상태에서 무조건 신청 완료 텍스트 표시됨
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
*/
