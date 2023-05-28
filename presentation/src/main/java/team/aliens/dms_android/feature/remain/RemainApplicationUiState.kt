package team.aliens.dms_android.feature.remain

import java.util.UUID
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput

data class RemainsApplicationUiState(
    val remainsApplicationTimeOutput: FetchRemainsApplicationTimeOutput,
    val remainsOptionsOutput: FetchRemainsOptionsOutput,
    val currentAppliedRemainsOption: String,
    val remainsOptionId: UUID?,
    val remainsApplicationErrorMessage: String,
) : UiState {
    companion object {
        fun initial() = RemainsApplicationUiState(
            remainsApplicationTimeOutput = FetchRemainsApplicationTimeOutput(
                startDayOfWeek = null,
                startTime = null,
                endDayOfWeek = null,
                endTime = null,
            ),
            remainsOptionsOutput = FetchRemainsOptionsOutput(
                remainOptions = emptyList(),
            ),
            remainsOptionId = null,
            currentAppliedRemainsOption = "",
            remainsApplicationErrorMessage = "",
        )
    }
}