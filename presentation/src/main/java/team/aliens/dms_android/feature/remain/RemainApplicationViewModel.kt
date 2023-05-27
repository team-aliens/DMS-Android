package team.aliens.dms_android.feature.remain

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.domain.exception.RemoteException
import team.aliens.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.domain.usecase.remain.FetchRemainsApplicationTimeUseCase
import team.aliens.domain.usecase.remain.FetchRemainsOptionsUseCase
import team.aliens.domain.usecase.remain.UpdateRemainsOptionUseCase
import team.aliens.presentation.R

@HiltViewModel
internal class RemainsApplicationViewModel @Inject constructor(
    private val fetchRemainsApplicationTimeUseCase: FetchRemainsApplicationTimeUseCase,
    private val fetchRemainsOptionsUseCase: FetchRemainsOptionsUseCase,
    private val fetchCurrentAppliedRemainsOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
    private val updateRemainOptionUseCase: UpdateRemainsOptionUseCase,
    private val application: Application = Application(),
) : MviViewModel<RemainsApplicationState, RemainsApplicationEvent>(RemainsApplicationState.initial()) {

    override fun updateState(event: RemainsApplicationEvent) {
        when (event) {
            is RemainsApplicationEvent.FetchAvailableRemainsTime -> {
                fetchRemainsApplicationTime()
            }

            is RemainsApplicationEvent.FetchRemainsOptions -> {
                fetchRemainsOptions()
            }

            is RemainsApplicationEvent.FetchCurrentAppliedRemainsOption -> {
                fetchCurrentAppliedRemainsOption()
            }

            is RemainsApplicationEvent.UpdateRemainsOption -> {
                setState(
                    newState = uiState.value.copy(
                        remainsOptionId = event.remainsOptionId,
                    )
                )
                updateRemainOption()
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
                onErrorEvent(
                    throwable = it,
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
                        remainsOptionsOutput = it,
                    )
                )
            }.onFailure {
                onErrorEvent(
                    throwable = it,
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
                    onErrorEvent(
                        throwable = it,
                    )
                }
            }
        }
    }

    private fun updateRemainOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                updateRemainOptionUseCase(
                    updateRemainsOptionInput = UpdateRemainsOptionInput(
                        remainsOptionId = uiState.value.remainsOptionId!!,
                    )
                )
            }.onSuccess {
                fetchCurrentAppliedRemainsOption()
            }.onFailure {
                onErrorEvent(
                    throwable = it,
                )
            }
        }
    }

    // TODO BaseViewModel (MviViewModel) 에서 처리할 수 있는 방법 생각해보기
    private fun onErrorEvent(
        throwable: Throwable,
    ) {
        setRemainsApplicationErrorMessage(
            message = when (throwable) {
                is RemoteException.BadRequest -> {
                    application.getString(R.string.error_bad_request)
                }

                is RemoteException.Unauthorized -> {
                    application.getString(R.string.error_unauthorized)
                }

                is RemoteException.Forbidden -> {
                    application.getString(R.string.error_forbidden)
                }

                is RemoteException.NotFound -> {
                    application.getString(R.string.error_not_found)
                }

                is RemoteException.TooManyRequests -> {
                    application.getString(R.string.error_too_many_request)
                }

                else -> {
                    application.getString(R.string.error_internal_server)
                }
            }
        )
    }

    private fun setRemainsApplicationErrorMessage(
        message: String,
    ) {
        setState(
            newState = uiState.value.copy(
                remainsApplicationErrorMessage = message,
            )
        )
    }
}