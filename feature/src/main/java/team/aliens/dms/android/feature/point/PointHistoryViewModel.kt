package team.aliens.dms.android.feature.point

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.point.model.Point
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.data.point.repository.PointRepository
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
internal class PointHistoryViewModel @Inject constructor(
    private val pointRepository: PointRepository,
) : BaseMviViewModel<PointHistoryUiState, PointHistoryIntent, PointHistorySideEffect>(
    initialState = PointHistoryUiState.initial(),
) {
    private lateinit var allPoints: List<Point>
    private var scoreOfAllPoints by Delegates.notNull<Int>()

    private lateinit var bonusPoints: List<Point>
    private var scoreOfBonusPoints by Delegates.notNull<Int>()

    private lateinit var minusPoints: List<Point>
    private var scoreOfMinusPoints by Delegates.notNull<Int>()

    init {
        fetchPoints()
    }

    override fun processIntent(intent: PointHistoryIntent) {
        when (intent) {
            is PointHistoryIntent.UpdateSelectedPointType -> updatePoints(pointType = intent.pointType)
        }
    }

    private fun fetchPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                pointRepository.fetchPoints(type = PointType.ALL)
            }.onSuccess { pointStatus ->
                this@PointHistoryViewModel.allPoints = pointStatus.points
                this@PointHistoryViewModel.scoreOfAllPoints = pointStatus.totalPoints

                val bonusPoints = pointStatus.points.filter { it.type == PointType.BONUS }
                this@PointHistoryViewModel.bonusPoints = bonusPoints
                this@PointHistoryViewModel.scoreOfBonusPoints = bonusPoints.sumOf { it.score }

                val minusPoints = pointStatus.points.filter { it.type == PointType.MINUS }
                this@PointHistoryViewModel.minusPoints = minusPoints
                this@PointHistoryViewModel.scoreOfMinusPoints = minusPoints.sumOf { it.score }
            }
        }
    }

    private fun updatePoints(pointType: PointType): Boolean = reduce(
        newState = stateFlow.value.copy(
            selectedPointType = pointType, totalPoints = when (pointType) {
                PointType.ALL -> scoreOfAllPoints
                PointType.BONUS -> scoreOfBonusPoints
                PointType.MINUS -> scoreOfMinusPoints
            }, points = when (pointType) {
                PointType.ALL -> allPoints
                PointType.BONUS -> bonusPoints
                PointType.MINUS -> minusPoints
            }
        ),
    )
}

internal data class PointHistoryUiState(
    val selectedPointType: PointType,
    val totalPoints: Int?,
    val points: List<Point>,
) : UiState() {
    companion object {
        fun initial() = PointHistoryUiState(
            selectedPointType = PointType.ALL,
            totalPoints = null,
            points = emptyList(),
        )
    }
}

internal sealed class PointHistoryIntent : Intent() {
    class UpdateSelectedPointType(val pointType: PointType) : PointHistoryIntent()
}

internal sealed class PointHistorySideEffect : SideEffect()

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms.android.feature._legacy.base.MviIntent
import team.aliens.dms.android.feature._legacy.base.MviSideEffect
import team.aliens.dms.android.feature._legacy.base.MviState
import team.aliens.dms.android.domain.model._common.PointType
import team.aliens.dms.android.domain.model.point.FetchPointsInput
import team.aliens.dms.android.domain.model.point.Point
import team.aliens.dms.android.domain.model.point.toModel
import team.aliens.dms.android.domain.usecase.point.FetchPointsUseCase
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
*/
