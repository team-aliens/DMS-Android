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
import team.aliens.domain.model.point.FetchPointsOutput
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
        fetchPoints(
            type = PointType.ALL,
        )
    }

    override fun processIntent(intent: PointHistoryIntent) {
        when (intent) {
            is PointHistoryIntent.FetchPoints -> this.fetchPoints(intent.pointType)
            PointHistoryIntent.FetchAllTypePoints -> this.fetchPoints(PointType.ALL)
            PointHistoryIntent.FetchBonusTypePoints -> this.fetchPoints(PointType.BONUS)
            PointHistoryIntent.FetchMinusTypePoints -> this.fetchPoints(PointType.MINUS)
        }
    }

    private fun fetchPoints(type: PointType) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchPointsUseCase(
                    fetchPointsInput = FetchPointsInput(
                        type = type,
                    ),
                )
            }.onSuccess {
                this@PointHistoryViewModel.setPoints(
                    type = type,
                    fetchPointsOutput = it,
                )
            }
        }
    }

    private fun setPoints(
        type: PointType,
        fetchPointsOutput: FetchPointsOutput,
    ) {
        reduce(
            newState = stateFlow.value.copy(
                selectedType = type,
                totalPoint = fetchPointsOutput.totalPoint,
                points = fetchPointsOutput.pointHistories.toModel(),
            ),
        )
    }
}

internal sealed class PointHistoryIntent : MviIntent {
    class FetchPoints(val pointType: PointType) : PointHistoryIntent()
    object FetchAllTypePoints : PointHistoryIntent()
    object FetchBonusTypePoints : PointHistoryIntent()
    object FetchMinusTypePoints : PointHistoryIntent()
}


internal data class PointHistoryState(
    val selectedType: PointType,
    val totalPoint: Int,
    val points: List<Point>,
) : MviState {
    companion object {
        fun initial() = PointHistoryState(
            selectedType = PointType.ALL,
            totalPoint = 0, // todo 스켈레톤 효과에 따른 리팩토링 논의
            points = emptyList(),
        )
    }
}

internal sealed class PointHistorySideEffect : MviSideEffect
