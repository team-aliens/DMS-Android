package team.aliens.dms_android.viewmodel.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.cafeteria.MealEvent
import team.aliens.dms_android.feature.cafeteria.MealList
import team.aliens.dms_android.feature.cafeteria.MealState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.entity.MealEntity
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.meal.RemoteMealUseCase
import team.aliens.domain.usecase.notice.RemoteCheckNewNoticeBooleanUseCase
import team.aliens.local_domain.usecase.meal.LocalMealUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val localMealUseCase: LocalMealUseCase,
    private val remoteMealUseCase: RemoteMealUseCase,
    private val remoteCheckNewNoticeBooleanUseCase: RemoteCheckNewNoticeBooleanUseCase,
) : BaseViewModel<MealState, MealEvent>() {

    private val _mealEvent = MutableEventFlow<Event>()
    val mealEvent = _mealEvent.asEventFlow()

    fun fetchMeal(date: LocalDate) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMealUseCase.execute(date)
                localMealUseCase.execute(date.toString())
            }.onSuccess {
                setState(state = state.value.copy(mealList = MealList(
                    breakfast = it.breakfast,
                    lunch = it.lunch,
                    dinner = it.dinner,
                )))
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(Event.BadRequestException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun noticeCheckBoolean() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteCheckNewNoticeBooleanUseCase.execute(Unit)
            }.onSuccess {
                setState(state = state.value.copy(noticeBoolean = it))
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(Event.BadRequestException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun fetchLocalMeal(date: LocalDate) {
        viewModelScope.launch {
            kotlin.runCatching {
                val response = localMealUseCase.execute(date.toString())
                state.value.mealList.breakfast = response.breakfast
                state.value.mealList.lunch = response.lunch
                state.value.mealList.dinner = response.dinner
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _mealEvent.emit(event)
        }
    }

    override val initialState: MealState
        get() = MealState.initial()

    override fun reduceEvent(oldState: MealState, event: MealEvent) {
        TODO("Not yet implemented")
    }

    fun updateDay(day: LocalDate) {
        setState(state = state.value.copy(
            today = day,
        ))
        fetchMeal(day)
    }

    sealed class Event {
        data class FetchMealSuccess(val mealEntity: MealEntity) : Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}