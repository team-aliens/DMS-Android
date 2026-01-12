package team.aliens.dms.android.feature.point.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.point.model.Point
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.data.point.repository.PointRepository
import javax.inject.Inject

@HiltViewModel
internal class PointHistoryViewModel @Inject constructor(
    val pointRepository: PointRepository
) : BaseStateViewModel<PointHistoryState, PointHistorySideEffect>(PointHistoryState()) {

    init {
        getPoints()
        initTab()
    }

    private fun getPoints() {
        viewModelScope.launch {
            pointRepository.fetchPoints(
                type = PointType.ALL,
                page = null,
                size = null,
            ).onSuccess { pointHistory ->
                val pointHistories = pointHistory.points
                val bonusPoints = pointHistories.filter { it.type == PointType.PLUS }
                val minusPoints = pointHistories.filter { it.type == PointType.MINUS }
                setState {
                    it.copy(
                        allPointList = pointHistories,
                        plusPointList = bonusPoints,
                        minusPointList = minusPoints,
                    )
                }
            }
        }
    }

    private fun initTab() {
        setState { it.copy(initialTab = 0) }
    }
}

internal data class PointHistoryState(
    val allPointList: List<Point> = emptyList(),
    val plusPointList: List<Point> = emptyList(),
    val minusPointList: List<Point> = emptyList(),
    val initialTab: Int = 0,
)

internal sealed interface PointHistorySideEffect

