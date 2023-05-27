package team.aliens.dms_android.feature.meal

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel1
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.Meal
import team.aliens.domain.usecase.meal.FetchMealUseCase
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val fetchMealUseCase: FetchMealUseCase,
) : BaseViewModel1<MealState, MealEvent>() {

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
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}
