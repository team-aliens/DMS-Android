package team.aliens.dms_android.feature.main.home.mypage.pointhistory

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseMviViewModel
import team.aliens.dms_android.base.MviIntent
import team.aliens.dms_android.base.MviSideEffect
import team.aliens.dms_android.base.MviState
import team.aliens.domain.model._common.PointType
import team.aliens.domain.model.point.FetchPointsInput
import team.aliens.domain.model.point.Point
import team.aliens.domain.model.point.toModel
import team.aliens.domain.usecase.point.FetchPointsUseCase
import javax.inject.Inject

@HiltViewModel
internal class PointHistoryViewModel @Inject constructor(
    private val fetchPointsUseCase: FetchPointsUseCase,
) : BaseMviViewModel<PointHistoryIntent, PointHistoryState, PointHistorySideEffect>(
    initialState = PointHistoryState.initial(),
) {
    init {
        fetchPoints()
    }

    override fun processIntent(intent: PointHistoryIntent) {
        when (intent) {
            is PointHistoryIntent.UpdateFilter -> updateFilter(intent.pointType)
        }
    }

    private fun updateFilter(type: PointType) {
        reduce(newState = stateFlow.value.copy(selectedType = type))
    }

    private fun fetchPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchPointsUseCase(
                    fetchPointsInput = FetchPointsInput(
                        type = PointType.ALL,
                    ),
                )
            }.onSuccess {
                initPoints(it.points.toModel())
            }
        }
    }

    private fun initPoints(points: List<Point>) {
        viewModelScope.launch(Dispatchers.Main) {
            val (totalAllPoints, allPoints) = points.extractTotalPointsAndPoints()
            val (totalBonusPoints, bonusPoints) = points.extractTotalPointsAndPointsOfBonus()
            val (totalMinusPoints, minusPoints) = points.extractTotalPointsAndPointsOfMinus()

            reduce(
                newState = stateFlow.value.copy(
                    totalAllPoints = totalAllPoints,
                    allPoints = allPoints,
                    totalBonusPoints = totalBonusPoints,
                    bonusPoints = bonusPoints,
                    totalMinusPoints = totalMinusPoints,
                    minusPoints = minusPoints,
                ),
            )
        }
    }
}

private fun List<Point>.extractTotalPointsAndPoints(
    type: PointType = PointType.ALL,
): Pair<Int, List<Point>> {
    return when (type) {
        PointType.ALL -> Pair(
            this.sumOf { point ->
                when (point.type) {
                    PointType.ALL -> throw IllegalStateException()
                    PointType.BONUS -> point.score
                    PointType.MINUS -> point.score * -1
                }
            },
            this,
        )

        PointType.BONUS -> this.filter { point ->
            point.type == PointType.BONUS
        }.run {
            Pair(
                this@run.sumOf { it.score },
                this@run,
            )
        }

        PointType.MINUS -> this.filter { point ->
            point.type == PointType.MINUS
        }.run {
            Pair(
                this@run.sumOf { it.score },
                this@run,
            )
        }
    }
}

private fun List<Point>.extractTotalPointsAndPointsOfBonus(): Pair<Int, List<Point>> =
    this.extractTotalPointsAndPoints(PointType.BONUS)

private fun List<Point>.extractTotalPointsAndPointsOfMinus(): Pair<Int, List<Point>> =
    this.extractTotalPointsAndPoints(PointType.MINUS)

internal sealed class PointHistoryIntent : MviIntent {
    class UpdateFilter(val pointType: PointType) : PointHistoryIntent()
}


internal data class PointHistoryState(
    val selectedType: PointType,
    val totalAllPoints: Int,
    val allPoints: List<Point>,
    val totalBonusPoints: Int,
    val bonusPoints: List<Point>,
    val totalMinusPoints: Int,
    val minusPoints: List<Point>,
) : MviState {
    companion object {
        fun initial() = PointHistoryState(
            selectedType = PointType.ALL,
            totalAllPoints = 0,
            allPoints = emptyList(),
            totalBonusPoints = 0,
            bonusPoints = emptyList(),
            totalMinusPoints = 0,
            minusPoints = emptyList(),
        )
    }
}

internal sealed class PointHistorySideEffect : MviSideEffect
