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
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.meal.RemoteMealUseCase
import team.aliens.local_domain.entity.meal.MealEntity
import team.aliens.local_domain.usecase.meal.LocalMealUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val localMealUseCase: LocalMealUseCase,
    private val remoteMealUseCase: RemoteMealUseCase,
) : BaseViewModel<MealState, MealEvent>() {

    init {
        state.value.selectedDay.run {
            fetchMealFromRemote(this)
            updateDay(this)
        }
    }

    override val initialState: MealState
        get() = MealState.getDefaultInstance()

    private val _mealEvent = MutableEventFlow<Event>()
    val mealEvent = _mealEvent.asEventFlow()

    private fun fetchMealFromRemote(
        date: LocalDate,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMealUseCase.execute(date)
            }.onSuccess {
                fetchMealFromLocal(state.value.selectedDay) // todo separate fetch meals from remote, save meals on local
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

    private fun fetchMealFromLocal(
        date: LocalDate,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                localMealUseCase.execute(date.toString())
            }.onSuccess {
                setMealState(it)
            }.onFailure {
                event(Event.UnknownException)
            }
        }
    }

    private fun setMealState(
        entity: MealEntity,
    ) {

        val breakfast = entity.breakfast.run {
            dropLast(1) to last() // menu list to kcal
        }

        val lunch = entity.lunch.run {
            dropLast(1) to last()
        }

        val dinner = entity.dinner.run {
            dropLast(1) to last()
        }

        viewModelScope.launch {
            state.value.mealList.emit(
                MealList(
                    breakfast = breakfast,
                    lunch = lunch,
                    dinner = dinner,
                )
            )
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _mealEvent.emit(event)
        }
    }

    override fun reduceEvent(oldState: MealState, event: MealEvent) {}

    internal fun updateDay(day: LocalDate) {

        if (day.month != state.value.selectedDay.month) {
            fetchMealFromRemote(day)
        }

        setState(
            state = state.value.copy(
                selectedDay = day,
            ),
        )

        fetchMealFromLocal(day)
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
