package team.aliens.dms.android.feature.meal.viewmodel

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.meal.model.Meal
import team.aliens.dms.android.data.meal.repository.MealRepository
import team.aliens.dms.android.feature.meal.ui.MealCardType
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.date.util.today
import javax.inject.Inject

@HiltViewModel
internal class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository, // TODO :: 필요한 경우 매핑 유스케이스 구현
) : BaseStateViewModel<MealState, Unit>(MealState()) {

    init {
        getMeal()
    }

    private fun getMeal(date: LocalDate? = null) {
        val selectedDate = date ?: uiState.value.selectedDate
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.fetchMeal(date = selectedDate)
                .onSuccess { successfulMeal ->
                    setState { it.copy(meal = successfulMeal) }
                }
        }
    }

    internal fun setDate(date: LocalDate) {
        setState { it.copy(selectedDate = date) }
        getMeal(date)
        hideCalendarBottomSheet()
    }

    internal fun setNextMealType() {
        val nextType = when (uiState.value.currentCardType) {
            MealCardType.BREAKFAST -> MealCardType.LUNCH
            MealCardType.LUNCH -> MealCardType.DINNER
            MealCardType.DINNER -> {
                setNextDate()
                MealCardType.BREAKFAST
            }
        }
        setState { it.copy(currentCardType = nextType) }
    }

    internal fun setPreviousMealType() {
        val previousType = when (uiState.value.currentCardType) {
            MealCardType.BREAKFAST -> {
                setPreviousDate()
                MealCardType.DINNER
            }
            MealCardType.LUNCH -> MealCardType.BREAKFAST
            MealCardType.DINNER -> MealCardType.LUNCH
        }
        setState { it.copy(currentCardType = previousType) }
    }

    internal fun setNextDate() {
        val date = uiState.value.selectedDate.plusDays(1)
        setState { it.copy(selectedDate = date) }
        getMeal(date)
    }

    internal fun setPreviousDate() {
        val date = uiState.value.selectedDate.minusDays(1)
        setState { it.copy(selectedDate = date) }
        getMeal(date)
    }

    internal fun showCalendarBottomSheet() {
        setState { it.copy(isShowCalendar = true) }
    }

    internal fun hideCalendarBottomSheet() {
        setState { it.copy(isShowCalendar = false) }
    }
}

@Immutable
internal data class MealState(
    val meal: Meal = Meal(),
    val selectedDate: LocalDate = today,
    val isShowCalendar: Boolean = false,
    val currentCardType: MealCardType = getProperMeal()
)

private const val BreakfastStartTime: Int = 9
private const val LunchStartTime: Int = 13
private const val DinnerStartTime: Int = 19


internal fun getProperMeal(): MealCardType = when (now.hour) {
    in BreakfastStartTime until LunchStartTime -> MealCardType.LUNCH
    in LunchStartTime until DinnerStartTime -> MealCardType.DINNER
    else -> MealCardType.BREAKFAST
}
