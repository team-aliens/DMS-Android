package team.aliens.dms_android.feature.main.home.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.Meal
import team.aliens.domain.usecase.meal.FetchMealUseCase
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase
import javax.inject.Inject

private const val MealDateFormat = "yyyy-MM-dd"

internal fun Date.toMealFormattedString(
    locale: Locale = Locale.getDefault(),
): String = SimpleDateFormat(MealDateFormat, locale).format(this)

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchWhetherNewNoticesExistUseCase: FetchWhetherNewNoticesExistUseCase,
    private val fetchMealUseCase: FetchMealUseCase,
) : MviViewModel<HomeUiState, HomeUiEvent>(
    initialState = HomeUiState.initial(),
) {
    init {
        fetchWhetherNewNoticesExist()
        fetchMeal()
    }

    override fun updateState(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.UpdateDate -> this.setDate(event.date)
        }
    }

    private fun setDate(date: Date) {
        setState(
            newState = uiState.value.copy(
                selectedDate = date,
            )
        )
        fetchMeal()
    }

    private fun setDate(date: Long) {
        this.setDate(Date(date))
    }

    private fun fetchWhetherNewNoticesExist() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchWhetherNewNoticesExistUseCase()
            }.onSuccess {
                if (it.newNotices) {
                    setState(
                        newState = uiState.value.copy(
                            newNotices = true,
                        ),
                    )
                }
            }
        }
    }

    private fun fetchMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchMealUseCase(
                    fetchMealInput = FetchMealInput(
                        date = uiState.value.selectedDate.toMealFormattedString(),
                    ),
                )
            }.onSuccess {
                this@HomeViewModel.setMeal(
                    meal = it,
                )
            }
        }
    }

    private fun setMeal(
        meal: Meal,
    ) {
        val breakfast = meal.breakfast.dropLast(1)
        val kcalOfBreakfast = meal.breakfast.last()
        val lunch = meal.lunch.dropLast(1)
        val kcalOfLunch = meal.lunch.last()
        val dinner = meal.dinner.dropLast(1)
        val kcalOfDinner = meal.dinner.last()

        setState(
            newState = uiState.value.copy(
                breakfast = breakfast,
                kcalOfBreakfast = kcalOfBreakfast,
                lunch = lunch,
                kcalOfLunch = kcalOfLunch,
                dinner = dinner,
                kcalOfDinner = kcalOfDinner,
            ),
        )
    }
}

internal data class HomeUiState(
    val newNotices: Boolean,
    val selectedDate: Date,
    val breakfast: List<String>,
    val kcalOfBreakfast: String?,
    val lunch: List<String>,
    val kcalOfLunch: String?,
    val dinner: List<String>,
    val kcalOfDinner: String?,
) : UiState {
    companion object {
        fun initial() = HomeUiState(
            newNotices = false,
            selectedDate = Date(),
            breakfast = emptyList(),
            kcalOfBreakfast = null,
            lunch = emptyList(),
            kcalOfLunch = null,
            dinner = emptyList(),
            kcalOfDinner = null,
        )
    }
}

internal sealed class HomeUiEvent : UiEvent {
    class UpdateDate(
        val date: Date,
    ) : HomeUiEvent()
}
