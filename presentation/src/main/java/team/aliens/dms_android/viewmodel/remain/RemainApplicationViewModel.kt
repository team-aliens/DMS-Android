package team.aliens.dms_android.viewmodel.remain

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.remain.RemainApplicationEvent
import team.aliens.dms_android.feature.remain.RemainApplicationState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.dms_android.viewmodel.remain.RemainApplicationViewModel.Event
import team.aliens.domain.entity.remain.AvailableRemainTimeEntity
import team.aliens.domain.entity.remain.RemainOptionsEntity
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.remain.RemoteFetchAvailableRemainTimeUseCase
import team.aliens.domain.usecase.remain.RemoteFetchCurrentRemainOptionsUseCase
import team.aliens.domain.usecase.remain.RemoteFetchRemainOptionsUseCase
import team.aliens.domain.usecase.remain.RemoteUpdateRemainOptionUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RemainApplicationViewModel @Inject constructor(
    private val updateRemainOptionUseCase: RemoteUpdateRemainOptionUseCase,
    private val fetchCurrentRemainOptionsUseCase: RemoteFetchCurrentRemainOptionsUseCase,
    private val fetchAvailableRemainTimeUseCase: RemoteFetchAvailableRemainTimeUseCase,
    private val fetchRemainOptionsUseCase: RemoteFetchRemainOptionsUseCase,
) : BaseViewModel<RemainApplicationState, RemainApplicationEvent>() {

    override val initialState: RemainApplicationState
        get() = RemainApplicationState.getDefaultInstance()

    private val _remainApplicationEffect = MutableEventFlow<Event>()
    val remainApplicationEffect = _remainApplicationEffect.asEventFlow()

    internal fun updateRemainOption() {
        viewModelScope.launch {
            kotlin.runCatching {
                updateRemainOptionUseCase.execute(state.value.remainOptionId)
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
                fetchCurrentRemainOptionsUseCase.execute(Unit)
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
                fetchAvailableRemainTimeUseCase.execute(Unit)
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
                fetchRemainOptionsUseCase.execute(Unit)
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
        data class AvailableRemainTime(val availableRemainTimeEntity: AvailableRemainTimeEntity) :
            Event()

        data class CurrentRemainOption(val title: String) : Event()
        data class RemainOptions(val remainOptionsEntity: RemainOptionsEntity) : Event()

        object BadRequestException : Event()
        object NotFoundException: Event()
        object UnauthorizedException : Event()
        object ForbiddenException : Event()
        object TooManyRequestException : Event()
        object ServerException : Event()
        object NullPointException: Event()
        object UnknownException: Event()
    }
}

// TODO 추후에 리팩토링 필요
private fun getEventFromThrowable(
    throwable: Throwable?,
): Event {
    return when(throwable){
        is BadRequestException -> Event.BadRequestException
        is NotFoundException -> Event.NotFoundException
        is UnauthorizedException -> Event.UnauthorizedException
        is ForbiddenException -> Event.ForbiddenException
        is TooManyRequestException -> Event.TooManyRequestException
        is ServerException -> Event.ServerException
        is NullPointerException -> Event.NullPointException
        else -> Event.UnknownException
    }
}