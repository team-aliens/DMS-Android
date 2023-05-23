package team.aliens.dms_android.feature.remain

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
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
import team.aliens.presentation.R

//@HiltViewModel
//class RemainApplicationViewModel @Inject constructor(
//    private val updateRemainOptionUseCase: UpdateRemainsOptionUseCase,
//    private val fetchCurrentRemainOptionsUseCase: FetchCurrentAppliedRemainsOptionUseCase,
//    private val fetchAvailableRemainTimeUseCase: FetchRemainsApplicationTimeUseCase,
//    private val fetchRemainOptionsUseCase: FetchRemainsOptionsUseCase,
//) : BaseViewModel1<RemainApplicationState, RemainApplicationEvent>() {
//
//    override val initialState: RemainApplicationState
//        get() = RemainApplicationState.getDefaultInstance()
//
//    private val _remainApplicationEffect = MutableEventFlow<Event>()
//    val remainApplicationEffect = _remainApplicationEffect.asEventFlow()
//
//    internal fun updateRemainOption() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                updateRemainOptionUseCase(
//                    updateRemainsOptionInput = UpdateRemainsOptionInput(
//                        remainsOptionId = state.value.remainOptionId,
//                    ),
//                )
//            }.onSuccess {
//                event(Event.UpdateRemainOption)
//            }.onFailure {
//                event(getEventFromThrowable(it))
//            }
//        }
//    }
//
//    internal fun fetchCurrentRemainOption() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                fetchCurrentRemainOptionsUseCase()
//            }.onSuccess {
//                event(Event.CurrentRemainOption(it.title))
//            }.onFailure {
//                event(getEventFromThrowable(it))
//            }
//        }
//    }
//
//    internal fun fetchAvailableRemainTime() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                fetchAvailableRemainTimeUseCase()
//            }.onSuccess {
//                event(Event.AvailableRemainTime(it))
//            }.onFailure {
//                event(getEventFromThrowable(it))
//            }
//        }
//    }
//
//    internal fun fetchRemainOptions() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                fetchRemainOptionsUseCase()
//            }.onSuccess {
//                event(Event.RemainOptions(it))
//            }.onFailure {
//                event(getEventFromThrowable(it))
//            }
//        }
//    }
//
//    private fun event(event: Event) {
//        viewModelScope.launch {
//            _remainApplicationEffect.emit(event)
//        }
//    }
//
//    internal fun setRemainOption(
//        remainOptionId: UUID,
//    ) {
//        sendEvent(RemainApplicationEvent.SetRemainOptionId(remainOptionId))
//    }
//
//    override fun reduceEvent(oldState: RemainApplicationState, event: RemainApplicationEvent) {
//        when (event) {
//            is RemainApplicationEvent.SetRemainOptionId -> {
//                setState(oldState.copy(remainOptionId = event.remainOptionId))
//            }
//        }
//    }
//
//    sealed class Event {
//        object UpdateRemainOption : Event()
//        data class AvailableRemainTime(val fetchRemainsApplicationTimeOutput: FetchRemainsApplicationTimeOutput) :
//            Event()
//
//        data class CurrentRemainOption(val title: String) : Event()
//        data class RemainOptions(val fetchRemainsOptionsOutput: FetchRemainsOptionsOutput) : Event()
//
//        object BadRequestException : Event()
//        object NotFoundException : Event()
//        object UnauthorizedException : Event()
//        object ForbiddenException : Event()
//        object TooManyRequestException : Event()
//        object ServerException : Event()
//        object NullPointException : Event()
//        object UnknownException : Event()
//    }
//}
//
//// TODO 추후에 리팩토링 필요
//private fun getEventFromThrowable(
//    throwable: Throwable?,
//): Event {
//    // fixme 추후에 리팩토링 필요
//    return when (throwable) {
//        is NullPointerException -> Event.NullPointException
//        else -> Event.UnknownException
//    }
//}

@HiltViewModel
internal class _RemainsApplicationViewModel @Inject constructor(
    private val fetchRemainsApplicationTimeUseCase: FetchRemainsApplicationTimeUseCase,
    private val fetchRemainsOptionsUseCase: FetchRemainsOptionsUseCase,
    private val fetchCurrentAppliedRemainsOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
    private val updateRemainOptionUseCase: UpdateRemainsOptionUseCase,
    private val application: Application = Application(),
) : MviViewModel<_RemainsApplicationState, _RemainsApplicationEvent>(_RemainsApplicationState.initial()) {

    override fun updateState(event: _RemainsApplicationEvent) {
        when (event) {
            is _RemainsApplicationEvent.FetchAvailableRemainsTime -> {
                fetchRemainsApplicationTime()
            }

            is _RemainsApplicationEvent.FetchRemainsOptions -> {
                fetchRemainsOptions()
            }

            is _RemainsApplicationEvent.FetchCurrentAppliedRemainsOption -> {
                fetchCurrentAppliedRemainsOption()
            }

            is _RemainsApplicationEvent.UpdateRemainsOption -> {
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
                onErrorEvent(
                    throwable = it,
                )
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

data class _RemainsApplicationState(
    val remainsApplicationTimeOutput: FetchRemainsApplicationTimeOutput,
    val remainsOptionsOutput: FetchRemainsOptionsOutput,
    val currentAppliedRemainsOption: String,
    val remainsOptionId: UUID?,
    val remainsApplicationErrorMessage: String,
) : UiState {
    companion object {
        fun initial() = _RemainsApplicationState(
            remainsApplicationTimeOutput = FetchRemainsApplicationTimeOutput(
                startDayOfWeek = DayOfWeek.MONDAY,
                startTime = "",
                endDayOfWeek = DayOfWeek.SUNDAY,
                endTime = "",
            ),
            remainsOptionsOutput = FetchRemainsOptionsOutput(
                remainOptions = emptyList(),
            ),
            remainsOptionId = null,
            currentAppliedRemainsOption = "",
            remainsApplicationErrorMessage = "",
        )
    }
}

sealed class _RemainsApplicationEvent : UiEvent {
    object FetchAvailableRemainsTime : _RemainsApplicationEvent()
    object FetchRemainsOptions : _RemainsApplicationEvent()
    object FetchCurrentAppliedRemainsOption : _RemainsApplicationEvent()
    class UpdateRemainsOption(
        val remainsOptionId: UUID,
    ) : _RemainsApplicationEvent()
}