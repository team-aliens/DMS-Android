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

    private lateinit var internalPoints: List<Point>

    override fun processIntent(intent: PointHistoryIntent) {
        when (intent) {
            is PointHistoryIntent.UpdateFilter -> updateFilter(intent.pointType)
        }
    }

    private fun updateFilter(type: PointType) {
        viewModelScope.launch(Dispatchers.Main) {
            val (totalPoints, filteredPoints) = if (type == PointType.ALL) {
                Pair(
                    internalPoints.sumOf { point ->
                        when (point.type) {
                            PointType.ALL -> throw IllegalStateException()
                            PointType.BONUS -> point.score
                            PointType.MINUS -> point.score * -1
                        }
                    },
                    internalPoints,
                )
            } else {
                val filtered = internalPoints.filter { it.type == type }

                Pair(
                    filtered.sumOf { it.score },
                    filtered,
                )
            }

            reduce(
                newState = stateFlow.value.copy(
                    selectedType = type,
                    totalPoints = totalPoints,
                    points = filteredPoints,
                ),
            )
        }
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
                val totalPoints = it.totalPoint
                val points = it.pointHistories.toModel()

                internalPoints = points
                reduce(
                    newState = stateFlow.value.copy(
                        totalPoints = totalPoints,
                        points = points,
                    ),
                )
            }
        }
    }
}

internal sealed class PointHistoryIntent : MviIntent {
    class UpdateFilter(val pointType: PointType) : PointHistoryIntent()
}


internal data class PointHistoryState(
    val selectedType: PointType,
    val totalPoints: Int,
    val points: List<Point>,
) : MviState {
    companion object {
        fun initial() = PointHistoryState(
            selectedType = PointType.ALL,
            totalPoints = 0, // todo 스켈레톤 효과에 따른 리팩토링 논의
            points = emptyList(),
        )
    }
}

internal sealed class PointHistorySideEffect : MviSideEffect
