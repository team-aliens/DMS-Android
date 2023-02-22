package team.aliens.dms_android.viewmodel.remain

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.remain.RemainApplicationEvent
import team.aliens.dms_android.feature.remain.RemainApplicationState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
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
        get() = RemainApplicationState.initial()

    private val _remainApplicationEffect = MutableEventFlow<Event>()
    val remainApplicationEffect = _remainApplicationEffect.asEventFlow()

    internal fun updateRemainOption() {
        viewModelScope.launch {
            kotlin.runCatching {
                updateRemainOptionUseCase.execute(state.value.remainOptionId)
            }.onSuccess {
                event(Event.UpdateRemainOption)
            }.onFailure {
                handleErrorException(it)
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
                handleErrorException(it)
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
                handleErrorException(it)
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
                handleErrorException(it)
            }
        }
    }

    internal fun setRemainOption(
        remainOptionId: UUID,
    ) {
        sendEvent(RemainApplicationEvent.SetRemainOptionId(remainOptionId))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _remainApplicationEffect.emit(event)
        }
    }

    private fun handleErrorException(
        throwable: Throwable,
    ) {
        when (throwable) {
            is BadRequestException -> event(Event.BadRequestException)
            is NotFoundException -> event(Event.NotFoundException)
            is TooManyRequestException -> event(Event.TooManyRequestException)
            is ServerException -> event(Event.ServerException)
            is UnauthorizedException -> event(Event.UnauthorizedException)
            else -> event(Event.UnknownException)
        }
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
        object NotFoundException : Event()
        object TooManyRequestException : Event()
        object ServerException : Event()
        object UnauthorizedException : Event()
        object UnknownException : Event()
    }
}