package team.aliens.dms_android.feature.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms_android.feature._legacy.base.MviIntent
import team.aliens.dms_android.feature._legacy.base.MviSideEffect
import team.aliens.dms_android.feature._legacy.base.MviState
import team.aliens.dms_android.domain.model.meal.FetchMealInput
import team.aliens.dms_android.domain.model.meal.Meal
import team.aliens.dms_android.domain.usecase.meal.FetchMealFromLocalOrRemoteIfNotExistsUseCase
import team.aliens.dms_android.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase
import javax.inject.Inject

private const val MealDateFormat = "yyyy-MM-dd"

internal fun Date.toMealFormattedString(
    locale: Locale = Locale.getDefault(),
): String = SimpleDateFormat(MealDateFormat, locale).format(this)

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchWhetherNewNoticesExistUseCase: FetchWhetherNewNoticesExistUseCase,
    private val fetchMealFromLocalOrRemoteIfNotExistsUseCase: FetchMealFromLocalOrRemoteIfNotExistsUseCase,
) : BaseMviViewModel<HomeIntent, HomeState, HomeSideEffect>(
    initialState = HomeState.initial(),
) {
    init {
        fetchWhetherNewNoticesExist()
        fetchMeal()
    }

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.UpdateDate -> this.setDate(intent.date)
        }
    }

    private fun setDate(date: Date) {
        reduce(
            newState = stateFlow.value.copy(
                selectedDate = date,
            )
        )
        fetchMeal()
    }

    private fun fetchWhetherNewNoticesExist() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchWhetherNewNoticesExistUseCase()
            }.onSuccess {
                if (it.newNotices) reduce(
                    newState = stateFlow.value.copy(
                        newNotices = true,
                    ),
                )
            }
        }
    }

    private fun fetchMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val formattedSelectedDate = stateFlow.value.selectedDate.toMealFormattedString()

                fetchMealFromLocalOrRemoteIfNotExistsUseCase(
                    fetchMealInput = FetchMealInput(
                        date = formattedSelectedDate,
                    ),
                )
            }.onSuccess { fetchedMeal -> setMeal(fetchedMeal) }
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

        reduce(
            newState = stateFlow.value.copy(
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

internal sealed class HomeIntent : MviIntent {
    class UpdateDate(val date: Date) : HomeIntent()
}

internal data class HomeState(
    val newNotices: Boolean,
    val selectedDate: Date,
    val breakfast: List<String>,
    val kcalOfBreakfast: String?,
    val lunch: List<String>,
    val kcalOfLunch: String?,
    val dinner: List<String>,
    val kcalOfDinner: String?,
) : MviState {
    companion object {
        fun initial() = HomeState(
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

internal sealed class HomeSideEffect : MviSideEffect
