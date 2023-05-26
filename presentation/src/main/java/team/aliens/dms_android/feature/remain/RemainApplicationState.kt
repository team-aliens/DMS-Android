package team.aliens.dms_android.feature.remain

import java.time.DayOfWeek
import java.util.UUID
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput

data class RemainsApplicationState(
    val remainsApplicationTimeOutput: FetchRemainsApplicationTimeOutput,
    val remainsOptionsOutput: FetchRemainsOptionsOutput,
    val currentAppliedRemainsOption: String,
    val remainsOptionId: UUID?,
    val remainsApplicationErrorMessage: String,
) : UiState {
    companion object {
        fun initial() = RemainsApplicationState(
            remainsApplicationTimeOutput = FetchRemainsApplicationTimeOutput(
                startDayOfWeek = DayOfWeek.MONDAY,
                startTime = "",
                endDayOfWeek = DayOfWeek.SUNDAY,
                endTime = "",
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