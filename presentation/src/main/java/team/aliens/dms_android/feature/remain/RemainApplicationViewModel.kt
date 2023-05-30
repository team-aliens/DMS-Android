package team.aliens.dms_android.feature.remain

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.exception.RemoteException
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.domain.usecase.remain.FetchRemainsApplicationTimeUseCase
import team.aliens.domain.usecase.remain.FetchRemainsOptionsUseCase
import team.aliens.domain.usecase.remain.UpdateRemainsOptionUseCase

@HiltViewModel
internal class RemainsApplicationViewModel @Inject constructor(
    private val fetchRemainsApplicationTimeUseCase: FetchRemainsApplicationTimeUseCase,
    private val fetchRemainsOptionsUseCase: FetchRemainsOptionsUseCase,
    private val fetchCurrentAppliedRemainsOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
    private val updateRemainOptionUseCase: UpdateRemainsOptionUseCase,
) : MviViewModel<RemainsApplicationUiState, RemainsApplicationUiEvent>(
    initialState = RemainsApplicationUiState.initial(),
) {

    init {
        fetchRemainsApplicationTime()
        fetchCurrentAppliedRemainsOption()
        fetchRemainsOptions()
    }

    override fun updateState(event: RemainsApplicationUiEvent) {
        when (event) {
            is RemainsApplicationUiEvent.FetchAvailableRemainsTime -> {
                fetchRemainsApplicationTime()
            }

            is RemainsApplicationUiEvent.FetchRemainsOptions -> {
                fetchRemainsOptions()
            }

            is RemainsApplicationUiEvent.FetchCurrentAppliedRemainsOption -> {
                fetchCurrentAppliedRemainsOption()
            }

            is RemainsApplicationUiEvent.UpdateUiRemainsOption -> {
                updateRemainOption()
            }

            is RemainsApplicationUiEvent.SetSelectedRemainsOption -> {
                setSelectedRemainsOption(
                    remainsOptionItemIndex = event.remainsOptionItemIndex,
                )
            }

            is RemainsApplicationUiEvent.SetRemainsApplicationButtonState -> {
                setRemainApplicationButtonState(
                    buttonEnabled = event.buttonEnabled,
                )
            }

        }
    }

    private fun fetchRemainsApplicationTime() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchRemainsApplicationTimeUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        remainsApplicationTimeOutput = it,
                    )
                )
            }.onFailure {
                setRemainsScreenError(
                    error = it,
                )
            }
        }
    }

    private fun fetchRemainsOptions() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchRemainsOptionsUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        remainsOptions = it.remainOptions.map { it.toRemainsOption() }
                    )
                )
            }.onFailure {
                setRemainsScreenError(
                    error = it,
                )
            }
        }
    }

    private fun fetchCurrentAppliedRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchCurrentAppliedRemainsOptionUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        currentAppliedRemainsOption = it.title,
                    )
                )
            }.onFailure {
                if (it !is RemoteException.NotFound) {
                    setRemainsScreenError(
                        error = it,
                    )
                }
            }
        }
    }

    private fun updateRemainOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val remainsOptionId = uiState.value.remainsOptionId

                if (remainsOptionId != null) {
                    updateRemainOptionUseCase(
                        updateRemainsOptionInput = UpdateRemainsOptionInput(
                            remainsOptionId = remainsOptionId,
                        )
                    )
                }
            }.onSuccess {
                fetchCurrentAppliedRemainsOption()
                setRemainApplicationButtonState(
                    buttonEnabled = uiState.value.currentAppliedRemainsOption == uiState.value.remainsOptionTitle,
                )
            }.onFailure {
                setRemainsScreenError(
                    error = it,
                )
            }
        }
    }

    private fun setSelectedRemainsOption(
        remainsOptionItemIndex: Int,
    ) {

        val remainsOption = uiState.value.remainsOptions[remainsOptionItemIndex]

        setState(
            newState = uiState.value.copy(
                remainsOptionTitle = remainsOption.title,
                remainsOptionId = remainsOption.id,
                error = null,
            )
        )
        setRemainApplicationButtonState(
            buttonEnabled = remainsOption.title != uiState.value.currentAppliedRemainsOption,
        )
    }

    private fun setRemainApplicationButtonState(
        buttonEnabled: Boolean,
    ) {
        setState(
            newState = uiState.value.copy(
                remainsApplicationButtonEnabled = buttonEnabled,
            )
        )
    }

    private fun setRemainsScreenError(
        error: Throwable,
    ){
        setState(
            newState = uiState.value.copy(
                error = error,
            )
        )
    }

    internal fun getRemainsOptionItemState(
        remainsOptionId: UUID,
    ): Boolean = uiState.value.remainsOptionId == remainsOptionId

    // TODO BaseViewModel (MviViewModel) 에서 처리할 수 있는 방법 생각해보기
}

internal data class RemainsApplicationUiState(
    val remainsApplicationTimeOutput: FetchRemainsApplicationTimeOutput?,
    val remainsOptions: List<RemainsOption>,
    val currentAppliedRemainsOption: String,
    val remainsOptionTitle: String,
    val remainsOptionId: UUID?,
    val remainsApplicationButtonEnabled: Boolean,
    val error: Throwable?,
) : UiState {
    companion object {
        fun initial() = RemainsApplicationUiState(
            remainsApplicationTimeOutput = null,
            remainsOptions = emptyList(),
            remainsOptionTitle = "",
            remainsOptionId = null,
            currentAppliedRemainsOption = "",
            remainsApplicationButtonEnabled = false,
            error = null,
        )
    }
}


internal sealed class RemainsApplicationUiEvent : UiEvent {
    object FetchAvailableRemainsTime : RemainsApplicationUiEvent()
    object FetchRemainsOptions : RemainsApplicationUiEvent()
    object FetchCurrentAppliedRemainsOption : RemainsApplicationUiEvent()
    object UpdateUiRemainsOption : RemainsApplicationUiEvent()
    class SetSelectedRemainsOption(
        val remainsOptionItemIndex: Int,
    ) : RemainsApplicationUiEvent()

    class SetRemainsApplicationButtonState(
        val buttonEnabled: Boolean,
    ) : RemainsApplicationUiEvent()
}

internal data class RemainsOption(
    val id: UUID,
    val title: String,
    val description: String,
    val isApplied: Boolean,
)

private fun FetchRemainsOptionsOutput.RemainsOptionInformation.toRemainsOption() = RemainsOption(
    id = this.id,
    title = this.title,
    description = this.description,
    isApplied = this.isApplied,
)