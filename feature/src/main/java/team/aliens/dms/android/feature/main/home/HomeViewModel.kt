package team.aliens.dms.android.feature.main.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.meal.exception.CannotFindMealException
import team.aliens.dms.android.data.meal.model.Meal
import team.aliens.dms.android.data.meal.repository.MealRepository
import team.aliens.dms.android.data.notice.repository.NoticeRepository
import team.aliens.dms.android.shared.date.util.today
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val noticeRepository: NoticeRepository,
) : BaseMviViewModel<HomeUiState, HomeIntent, HomeSideEffect>(
    initialState = HomeUiState.initial(),
) {
    init {
        fetchWhetherNewNoticeExists()
    }

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.UpdateSelectedDate -> updateMeal(intent.selectedDate)
        }
    }

    private fun fetchWhetherNewNoticeExists() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                noticeRepository.fetchWhetherNewNoticesExist()
            }.onSuccess { newNoticesExist ->
                reduce(newState = stateFlow.value.copy(newNoticesExist = newNoticesExist))
            }
        }
    }

    private fun updateMeal(date: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                mealRepository.fetchMeal(date)
            }.onSuccess { meal ->
                reduce(
                    newState = stateFlow.value.copy(
                        selectedDate = date,
                        currentMeal = meal,
                    ),
                )
                also { println("SDIESDIE ${stateFlow.value}") }
            }.onFailure { exception ->
                when (exception) {
                    is CannotFindMealException -> postSideEffect(HomeSideEffect.CannotFindMeal)
                }
            }
        }
    }
}

internal data class HomeUiState(
    val newNoticesExist: Boolean,
    val selectedDate: LocalDate,
    val currentMeal: Meal,
) : UiState() {
    companion object {
        fun initial(): HomeUiState {
            /**
             * to avoid difference between [HomeUiState.selectedDate] and [HomeUiState.currentMeal]'s date
             */
            val today = today
            return HomeUiState(
                newNoticesExist = false,
                selectedDate = today,
                currentMeal = Meal(
                    date = today,
                    breakfast = emptyList(),
                    kcalOfBreakfast = null,
                    lunch = emptyList(),
                    kcalOfLunch = null,
                    dinner = emptyList(),
                    kcalOfDinner = null,
                ),
            )
        }
    }
}

internal sealed class HomeIntent : Intent() {
    class UpdateSelectedDate(val selectedDate: LocalDate) : HomeIntent()
}

internal sealed class HomeSideEffect : SideEffect() {
    data object CannotFindMeal : HomeSideEffect()
}

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain.model.meal.FetchMealInput
import team.aliens.dms.android.domain.model.meal.Meal
import team.aliens.dms.android.domain.usecase.meal.FetchMealFromLocalOrRemoteIfNotExistsUseCase
import team.aliens.dms.android.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase
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
*/
