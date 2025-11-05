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
    private var allPoints: List<Point> = emptyList()
    private var scoreOfAllPoints by Delegates.notNull<Int>()

    private var bonusPoints: List<Point> = emptyList()
    private var scoreOfBonusPoints by Delegates.notNull<Int>()

    private var minusPoints: List<Point> = emptyList()
    private var scoreOfMinusPoints by Delegates.notNull<Int>()

    override fun processIntent(intent: PointHistoryIntent) {
        when (intent) {
            is PointHistoryIntent.UpdateSelectedPointType -> updatePoints(pointType = intent.pointType)
        }
    }

    internal fun fetchPoints(pointType: PointType) {
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

                updatePoints(pointType)
            }.onFailure {
                postSideEffect(PointHistorySideEffect.FetchPointError)
            }
        }
    }

    private fun updatePoints(pointType: PointType): Boolean = reduce(
        newState = stateFlow.value.copy(
            selectedPointType = pointType,
            totalPoints = when (pointType) {
                PointType.ALL -> scoreOfAllPoints
                PointType.BONUS -> scoreOfBonusPoints
                PointType.MINUS -> scoreOfMinusPoints
            },
            points = when (pointType) {
                PointType.ALL -> allPoints
                PointType.BONUS -> bonusPoints
                PointType.MINUS -> minusPoints
            },
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

internal sealed class PointHistorySideEffect : SideEffect() {
    data object FetchPointError : PointHistorySideEffect()
}
