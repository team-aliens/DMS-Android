package team.aliens.dms_android.viewmodel.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.cafeteria.FormedMeal
import team.aliens.dms_android.feature.cafeteria.MealEvent
import team.aliens.dms_android.feature.cafeteria.MealState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.meal.FetchMealInput
import team.aliens.domain._model.meal.Meal
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.meal.FetchMealUseCase
import team.aliens.local_domain.entity.meal.MealEntity
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val fetchMealUseCase: FetchMealUseCase,
) : BaseViewModel<MealState, MealEvent>() {

    init {
        state.value.selectedDay.run {
            fetchMeal(this.toString())
            updateDay(this)
        }
    }

    override val initialState: MealState
        get() = MealState.getDefaultInstance()

    private val _mealEvent = MutableEventFlow<Event>()
    val mealEvent = _mealEvent.asEventFlow()

    private fun fetchMeal(
        date: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchMealUseCase(
                    fetchMealInput = FetchMealInput(
                        date = date,
                    ),
                )
            }.onSuccess {
                setMealState(it)
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

    /*private fun fetchMealFromLocal(
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
    }*/
    private fun setMealState(
        meal: Meal,
    ) {

        val breakfast = meal.breakfast.run {
            dropLast(1) to last() // menu list to kcal
        }

        val lunch = meal.lunch.run {
            dropLast(1) to last()
        }

        val dinner = meal.dinner.run {
            dropLast(1) to last()
        }

        viewModelScope.launch {
            state.value.meals.emit(
                FormedMeal(
                    breakfast = breakfast,
                    lunch = lunch,
                    dinner = dinner,
                ),
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

        //if (day.month != state.value.selectedDay.month) {
        fetchMeal(day.toString())
        //}

        setState(
            state = state.value.copy(
                selectedDay = day,
            ),
        )
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
