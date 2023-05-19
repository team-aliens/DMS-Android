package team.aliens.dms_android.feature.remain

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseViewModel1
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.dms_android.feature.remain.RemainApplicationViewModel.Event
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.domain.usecase.remain.FetchRemainsApplicationTimeUseCase
import team.aliens.domain.usecase.remain.FetchRemainsOptionsUseCase
import team.aliens.domain.usecase.remain.UpdateRemainsOptionUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RemainApplicationViewModel @Inject constructor(
    private val updateRemainOptionUseCase: UpdateRemainsOptionUseCase,
    private val fetchCurrentRemainOptionsUseCase: FetchCurrentAppliedRemainsOptionUseCase,
    private val fetchAvailableRemainTimeUseCase: FetchRemainsApplicationTimeUseCase,
    private val fetchRemainOptionsUseCase: FetchRemainsOptionsUseCase,
) : BaseViewModel1<RemainApplicationState, RemainApplicationEvent>() {

    override val initialState: RemainApplicationState
        get() = RemainApplicationState.getDefaultInstance()

    private val _remainApplicationEffect = MutableEventFlow<Event>()
    val remainApplicationEffect = _remainApplicationEffect.asEventFlow()

    internal fun updateRemainOption() {
        viewModelScope.launch {
            kotlin.runCatching {
                updateRemainOptionUseCase(
                    updateRemainsOptionInput = UpdateRemainsOptionInput(
                        remainsOptionId = state.value.remainOptionId,
                    ),
                )
            }.onSuccess {
                event(Event.UpdateRemainOption)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun fetchCurrentRemainOption() {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchCurrentRemainOptionsUseCase()
            }.onSuccess {
                event(Event.CurrentRemainOption(it.title))
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun fetchAvailableRemainTime() {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchAvailableRemainTimeUseCase()
            }.onSuccess {
                event(Event.AvailableRemainTime(it))
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    internal fun fetchRemainOptions() {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchRemainOptionsUseCase()
            }.onSuccess {
                event(Event.RemainOptions(it))
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _remainApplicationEffect.emit(event)
        }
    }

    internal fun setRemainOption(
        remainOptionId: UUID,
    ) {
        sendEvent(RemainApplicationEvent.SetRemainOptionId(remainOptionId))
    }

    override fun reduceEvent(oldState: RemainApplicationState, event: RemainApplicationEvent) {
        when (event) {
            is RemainApplicationEvent.SetRemainOptionId -> {
                setState(oldState.copy(remainOptionId = event.remainOptionId))
            }
        }
    }

    sealed class Event {
        object UpdateRemainOption : Event()
        data class AvailableRemainTime(val fetchRemainsApplicationTimeOutput: FetchRemainsApplicationTimeOutput) :
            Event()

        data class CurrentRemainOption(val title: String) : Event()
        data class RemainOptions(val fetchRemainsOptionsOutput: FetchRemainsOptionsOutput) : Event()

        object BadRequestException : Event()
        object NotFoundException : Event()
        object UnauthorizedException : Event()
        object ForbiddenException : Event()
        object TooManyRequestException : Event()
        object ServerException : Event()
        object NullPointException : Event()
        object UnknownException : Event()
    }
}

// TODO 추후에 리팩토링 필요
private fun getEventFromThrowable(
    throwable: Throwable?,
): Event {
    // fixme 추후에 리팩토링 필요
    return when (throwable) {
        is NullPointerException -> Event.NullPointException
        else -> Event.UnknownException
    }
}