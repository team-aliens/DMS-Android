package team.aliens.dms_android.feature.main.home.mypage.pointhistory

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
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
) : MviViewModel<PointHistoryUiState, PointHistoryUiEvent>(
    initialState = PointHistoryUiState.initial(),
) {
    init {
        fetchPoints(
            type = PointType.ALL,
        )
    }

    override fun updateState(event: PointHistoryUiEvent) {
        when (event) {
            is PointHistoryUiEvent.FetchPoints -> this.fetchPoints(event.pointType)
            PointHistoryUiEvent.FetchAllTypePoints -> this.fetchPoints(PointType.ALL)
            PointHistoryUiEvent.FetchBonusTypePoints -> this.fetchPoints(PointType.BONUS)
            PointHistoryUiEvent.FetchMinusTypePoints -> this.fetchPoints(PointType.MINUS)
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
        setState(
            newState = uiState.value.copy(
                selectedType = type,
                totalPoint = fetchPointsOutput.totalPoint,
                points = fetchPointsOutput.pointHistories.toModel(),
            ),
        )
    }
}

internal data class PointHistoryUiState(
    val selectedType: PointType,
    val totalPoint: Int,
    val points: List<Point>,
) : UiState {
    companion object {
        fun initial() = PointHistoryUiState(
            selectedType = PointType.ALL,
            totalPoint = 0, // todo 스켈레톤 효과에 따른 리팩토링 논의
            points = emptyList(),
        )
    }
}

internal sealed class PointHistoryUiEvent : UiEvent {
    class FetchPoints(val pointType: PointType) : PointHistoryUiEvent()
    object FetchAllTypePoints : PointHistoryUiEvent()
    object FetchBonusTypePoints : PointHistoryUiEvent()
    object FetchMinusTypePoints : PointHistoryUiEvent()
}
