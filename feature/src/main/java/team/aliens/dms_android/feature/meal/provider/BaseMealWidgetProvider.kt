package team.aliens.dms_android.feature.meal.provider
/*
import android.appwidget.AppWidgetProvider
import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms_android.widget.meal.MealState
import team.aliens.dms_android.widget.meal.MealType
import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput
import team.aliens.domain.usecase.meal.FetchMealsUseCase
import team.aliens.presentation.R
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

*//**
 * FIXME
 *
 * Widget 에서 데이터를 WorkerManager 를 통해 작업 도중 생긴 문제입니다.
 * [onUpdate] 에서 [MealWorker]를 호출하는 것은 아래와 같은 "의도된 버그" 를 유발합니다.
 * https://issuetracker.google.com/issues/115575872#comment22
 *
 * (요약) WorkerManager 가 종료되면 다시 [onUpdate] 를 호출해서 무한 요청하는 버그가 존재합니다.
 * 따라서 [onUpdate] 에서 [startMealWorker]는 사용하지 말아야 합니다.
 *
 * WorkerManager 를 통해 Widget 을 업데이트 할 수 있는 방법을 고민해봐야 합니다.
 * *//*
@AndroidEntryPoint
abstract class BaseMealWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var fetchMealsUseCase: FetchMealsUseCase

    *//**
     * [MealType.getCurrentMealType] 를 통해 가져온 급식 타입에 맞는 급식 상태를 가져옵니다.
     * 급식 불러오기에 실패한다면 "급식이 없는 상태" 를 반환합니다.
     *//*
    suspend fun getMealState(
        context: Context,
    ): MealState {
        val nowDate = LocalDate.now().toString()
        val nowDateTime = LocalDateTime.now()

        var mealEntity = FetchMealsOutput.MealInformation(
            date = LocalDate.now().toString(),
            breakfast = listOf(context.getString(R.string.MealNotFound)),
            lunch = listOf(context.getString(R.string.MealNotFound)),
            dinner = listOf(context.getString(R.string.MealNotFound)),
        )

        kotlin.runCatching {
            fetchMealsUseCase(
                fetchMealsInput = FetchMealsInput(
                    date = nowDate,
                ),
            )
        }.onSuccess { result ->
            mealEntity = result.meals.first { it.date == nowDateTime.toString() }
        }

        val currentMealType = MealType.getCurrentMealType(nowDateTime)

        val meal = when (currentMealType) {
            MealType.Breakfast -> mealEntity.breakfast
            MealType.Lunch -> mealEntity.lunch
            MealType.Dinner -> mealEntity.dinner
        }

        val mealNotFound: Boolean = meal.size > 1

        return MealState(
            mealType = currentMealType,
            meal = if (mealNotFound) meal.dropLast(1)
                .joinToString("\n") else context.getString(R.string.MealNotFound),
            calories = if (mealNotFound) meal.last() else ""
        )
    }
}*/
